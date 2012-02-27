// File:     aes.c
// Author:   Burke Libbey <burke@burkelibbey.org>
// Modified: <2009-11-24 19:38:47 CST>

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NK 4 // number of words in the key
#define NB 4 // number of bytes in a word
#define NR 10 // number of rounds

// irreducible polynomial. 
#define POLY (0x11b)

typedef unsigned char byte;
typedef uint32_t word;

// Round constants
const int Rcon[] = {
  0x00000000, 0x01000000, 0x02000000, 0x04000000, 0x08000000, 0x10000000,
  0x20000000, 0x40000000, 0x80000000, 0x1b000000, 0x36000000
};

// forward and reverse S-boxes
static byte sbox[256];
static byte inv_sbox[256];

word // converts an array of bytes to a word (reversing byte order)
unbytes(byte* bytes)
{
  word word;
  int i;
  word = bytes[0];
  for (i = 1; i < NB; ++i) {
    word <<= 8;
    word |= bytes[i];
  }
  return word;
}

void // Converts a word to an array of bytes, in the supplied *bytes pointer.
bytes(word word, byte *bytes)
{
  int i;
  for (i = 0; i < NB; ++i) {
    bytes[i] = ((word & (0xFF << (NB-i-1)*8)) >> (NB-i-1)*8);
  }
}

word // Rotate word. Used in each encryption round.
rot_word(word word)
{                                          // word = ABCD
  byte temp = ((word & 0xFF000000) >> 24); // temp = A
  word <<= 8;                              // word = BCD0
  word |= temp;                            // word = BCDA
  return word;
}

word // Substitute word -- use the S-box for encryption.
sub_word(word word)
{
  int i;
  byte b[NB];
  bytes(word, b);
  for (i = 0; i < NB; ++i) {
    b[i] = sbox[b[i]];
  }
  return unbytes(b);
}

word * // Given an encryption key, generate the key schedule used for encryption.
key_schedule(byte *key)
{
  static word schedule[44];
  word temp;
  int i;

  // Read the key into the first four locations.
  for (i = 0; i < 4; ++i) {
    schedule[i] = 0;
    schedule[i] |= (*(key+(NB*i)+0 )) << 24;
    schedule[i] |= (*(key+(NB*i)+1 )) << 16;
    schedule[i] |= (*(key+(NB*i)+2))  << 8;
    schedule[i] |= (*(key+(NB*i)+3))  << 0;
  }

  for (i = NK; i < 44; ++i) {
    temp = schedule[i - 1];
    if (i % NK == 0) {
      temp = rot_word(temp);
      temp = sub_word(temp);
      temp ^= Rcon[i/NK];
    }
    schedule[i] = schedule[i - NK] ^ temp;
  }

  return schedule;
}

void // for each element in the state matrix, replace it with a lookup to the S-box.
sub_bytes(byte state[][4])
{
  int i, j;
  for (i = 0; i < 4; ++i) {
    for (j = 0; j < 4; ++j) {
      state[i][j] = sbox[state[i][j]];
    }
  }
}

void // Use the inverse S-box to reverse sub_bytes on the state matrix
inv_sub_bytes(byte state[][4])
{
  int i, j;
  for (i = 0; i < 4; ++i) {
    for (j = 0; j < 4; ++j) {
      state[i][j] = inv_sbox[state[i][j]];
    }
  }
}

void // Extremely naive approach. Shift rows for an encryption round.
shift_rows(byte state[][4])
{
  byte temp;
  temp = state[1][0];
  state[1][0] = state[1][1];
  state[1][1] = state[1][2];
  state[1][2] = state[1][3];
  state[1][3] = temp;

  temp = state[2][0];
  state[2][0] = state[2][2];
  state[2][2] = temp;
  temp = state[2][1];
  state[2][1] = state[2][3];
  state[2][3] = temp;

  temp = state[3][0];
  state[3][0] = state[3][3];
  state[3][3] = state[3][2];
  state[3][2] = state[3][1];
  state[3][1] = temp;
}


void // Unshift rows, ie. undo shift_rows. Used for decryption.
inv_shift_rows(byte state[][4])
{
  byte temp;

  temp = state[1][0];
  state[1][0] = state[1][3];
  state[1][3] = state[1][2];
  state[1][2] = state[1][1];
  state[1][1] = temp;

  temp = state[2][0];
  state[2][0] = state[2][2];
  state[2][2] = temp;
  temp = state[2][1];
  state[2][1] = state[2][3];
  state[2][3] = temp;

  temp = state[3][0];
  state[3][0] = state[3][1];
  state[3][1] = state[3][2];
  state[3][2] = state[3][3];
  state[3][3] = temp;
}


byte // Multiply x by two.
xt(byte x)
{
  if (x & 0x80) {
    x <<= 1;
    x ^= POLY;
  } else {
    x <<= 1;
  }
  return x;
}

// Convenience functions to calculate x*n
byte mult_1(byte x) { return                                     x; }
byte mult_2(byte x) { return                             xt(x)    ; }
byte mult_3(byte x) { return                             xt(x) ^ x; }
byte mult_4(byte x) { return                 xt(xt(x))            ; }
byte mult_5(byte x) { return                 xt(xt(x))         ^ x; }
byte mult_6(byte x) { return                 xt(xt(x)) ^ xt(x)    ; }
byte mult_7(byte x) { return                 xt(xt(x)) ^ xt(x) ^ x; }
byte mult_8(byte x) { return xt(xt(xt(x)))                        ; }
byte mult_9(byte x) { return xt(xt(xt(x)))                     ^ x; }
byte mult_a(byte x) { return xt(xt(xt(x)))             ^ xt(x)    ; }
byte mult_b(byte x) { return xt(xt(xt(x)))             ^ xt(x) ^ x; }
byte mult_c(byte x) { return xt(xt(xt(x))) ^ xt(xt(x))            ; }
byte mult_d(byte x) { return xt(xt(xt(x))) ^ xt(xt(x))         ^ x; }
byte mult_e(byte x) { return xt(xt(xt(x))) ^ xt(xt(x)) ^ xt(x)    ; }
byte mult_f(byte x) { return xt(xt(xt(x))) ^ xt(xt(x)) ^ xt(x) ^ x; }


void // Perform the MixColumns operation for encryption defined in the FIPS 197 document.
mix_columns(byte s[][4])
{
  int c;
  byte p[4]; //previous values.
  for (c = 0; c < 4; ++c) {
    p[0] = s[0][c];
    p[1] = s[1][c];
    p[2] = s[2][c];
    p[3] = s[3][c];

    s[0][c] = mult_2(p[0]) ^ mult_3(p[1]) ^ p[2] ^ p[3];
    s[1][c] = mult_2(p[1]) ^ mult_3(p[2]) ^ p[3] ^ p[0];
    s[2][c] = mult_2(p[2]) ^ mult_3(p[3]) ^ p[0] ^ p[1];
    s[3][c] = mult_2(p[3]) ^ mult_3(p[0]) ^ p[1] ^ p[2];
  }
}

void // inverse of mix_columns, used for decryption
inv_mix_columns(byte s[][4])
{
  int c;
  byte p[4]; //previous values.
  for (c = 0; c < 4; ++c) {
    p[0] = s[0][c];
    p[1] = s[1][c];
    p[2] = s[2][c];
    p[3] = s[3][c];

    s[0][c] = mult_e(p[0]) ^ mult_b(p[1]) ^ mult_d(p[2]) ^ mult_9(p[3]);
    s[1][c] = mult_9(p[0]) ^ mult_e(p[1]) ^ mult_b(p[2]) ^ mult_d(p[3]);
    s[2][c] = mult_d(p[0]) ^ mult_9(p[1]) ^ mult_e(p[2]) ^ mult_b(p[3]);
    s[3][c] = mult_b(p[0]) ^ mult_d(p[1]) ^ mult_9(p[2]) ^ mult_e(p[3]);
  }
}

void // Given an intermediate string, xor it with the round key to get the round output.
add_round_key(byte state[][4], int round, word *schedule)
{
  int c, r;
  word word;
  byte key_bytes[4];
  
  for (c = 0; c < 4; ++c) {
    word = schedule[round*4 + c];
    bytes(word, key_bytes); 
    for (r = 0; r < 4; ++r) {
      state[r][c] ^= key_bytes[r];
    }
  }
}

void // Print the contents of the state matrix, nicely formatted.
print_state(byte state[][4])
{
  int i;
  for (i = 0; i < 4; ++i) {
    printf("%02x  %02x  %02x  %02x     ",
           state[0][i],
           state[1][i],
           state[2][i],
           state[3][i]);
  }
  printf("\n\n");
}

void // Perform AES-128 encryption on a given plaintext, with a given schedule, writing the result into ciphertext.
encrypt(const byte *plaintext, byte *ciphertext, const word *schedule)
{
  byte state[4][4];
  int round, i, r, c;

  printf("ENCRYPTION PROCESS\n------------------\n");

  for (r = 0; r < 4; ++r) {
    for (c = 0; c < 4; ++c) {
      state[r][c] = plaintext[r+(4*c)];
    }
  }

  printf("Plain Text:\n");
  print_state(state);

  add_round_key(state, 0, schedule);

  printf("Round %d\n---------\n",1);
  print_state(state);
  
  for (round = 1; round < NR; ++round) {
    sub_bytes(state);
    shift_rows(state);
    mix_columns(state);
    add_round_key(state, round, schedule);
    if (round == NR-1)
      printf("Last Round\n---------\n");
    else 
      printf("Round %d\n---------\n",round+1);
    print_state(state);
  }
  sub_bytes(state);
  shift_rows(state);
  add_round_key(state, round, schedule);
  printf("CipherText:\n");
  print_state(state);

  memcpy(ciphertext, state, 16);
}


void // Decrypt AES-128, using the given ciphertext and key schedule, writing the result into plaintext.
decrypt(const byte *ciphertext, byte *plaintext, const word *schedule)
{
  byte state[4][4];
  int round, i, r, c;

  printf("DECRYPTION PROCESS\n------------------\n");

  for (r = 0; r < 4; ++r) {
    for (c = 0; c < 4; ++c) {
      state[c][r] = ciphertext[r+(4*c)];
    }
  }

  printf("CipherText:\n");
  print_state(state);
  
  add_round_key(state, NR, schedule);
  for (round = NR-1; round > 0; --round) {
    inv_shift_rows(state);
    inv_sub_bytes(state);

    printf("Round %d\n---------\n",round);
    print_state(state);

    add_round_key(state, round, schedule);
    inv_mix_columns(state);
  }
  inv_shift_rows(state);
  inv_sub_bytes(state);
  add_round_key(state, 0, schedule);

  printf("Plaintext:\n");
  print_state(state);

  memcpy(ciphertext, state, 16);

}
  
int // Read in input files, perform encryption, then decryption, and exit.
main(int argc, char **argv)
{
  byte *sbox_fname, *inv_sbox_fname, *plaintext_fname, *key_fname;
  word *schedule;
  FILE *fp;
  byte key[16], plaintext[16], ciphertext[16];
  unsigned int z, i;
  
  if (argc != 5) {
    printf("Usage: ./aes sbox.txt inv_sbox.txt plaintext.txt key.txt\n");
    exit(1);
  }

  sbox_fname      = argv[1];
  inv_sbox_fname  = argv[2];
  plaintext_fname = argv[3];
  key_fname       = argv[4];
  
  // Read in the sbox
  fp = fopen(sbox_fname, "r");
  i = 0;
  while (!feof(fp) && i<256) {
    fscanf(fp, "%X", &z);
    sbox[i++] = z;
  }
  fclose(fp);

  // Read in the inverse sbox
  fp = fopen(inv_sbox_fname, "r");
  i = 0;
  while (!feof(fp) && i<256) {
    fscanf(fp, "%X", &z);
    inv_sbox[i++] = z;
  }
  fclose(fp);

  // Read in the plaintext
  printf("PlainText Filename:%s\n", plaintext_fname);
  fp = fopen(plaintext_fname, "r");
  i = 0;
  while (!feof(fp) && i < (NB * NK)) {
    fscanf(fp, "%X", &z);
    plaintext[i++] = z;
  }
  fclose(fp);

  // Read in the key
  printf("Key Filename:%s\n\n", key_fname);
  fp = fopen(key_fname, "r");
  i = 0;
  while (!feof(fp) && i < (NB * NK)) {
    fscanf(fp, "%X", &z);
    key[i++] = z;
  }
  fclose(fp);

  printf("Key Schedule:\n");
  schedule = key_schedule(key);
  for (i = 0; i < 44; ++i) {
    if ((i+1)%4) {
      printf("%08x,",schedule[i]);
    } else {
      printf("%08x\n",schedule[i]);
    }
  }

  encrypt(plaintext, ciphertext, schedule);

  decrypt(ciphertext, plaintext, schedule);

  printf("End of Processing\n");
}
