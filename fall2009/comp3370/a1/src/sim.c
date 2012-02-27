// File:     sim.c
// Author:   Burke Libbey
// Modified: <2009-10-11 13:04:41 CDT>

#include <limits.h>
#include <stdio.h>
#include <stdlib.h>

#include "architecture.h"
#include "bitmacros.h"
#include "instructions.h"

char *
toAscii(word_t word)
{
  char *ret = calloc(3, sizeof(char));
  char char1 = word & 0x00FF;
  char char2 = word >> 8;
  ret[0] = ( char1 > 0x21 && char1 < 0x7e ) ? char1 : '.';
  ret[1] = ( char2 > 0x21 && char2 < 0x7e ) ? char2 : '.';
  return ret;
}

void
printData(cpu_t *cpu, int limit)
{
  int i, j;
  if (limit == 0)
    limit = INT_MAX;
  for (i=0; (i < DATASPACE / 8 && i < limit); ++i) {
    for (j=0; j<8; ++j)
      printf("%04X ", cpu->data[8*i+j]);
    printf("\t'");
    for (j=0; j<8; ++j)
      printf("%s", toAscii(cpu->data[8*i+j]));
    printf("'\n");
  }
}

void
printError(cpu_t *cpu)
{
  switch (cpu->error) {
  case INVALID_INSTRUCTION:
    printf("Illegal instruction %04X detected at address %04X.\n\n",
           cpu->code[cpu->pc], cpu->pc);
    break;
  case INSTRUCTION_OUT_OF_BOUNDS:
    printf("Attempted to load Instruction from Out-of-Bounds Memory Location. Terminating.\n\n");
    break;
  case INFINITE_LOOP:
    printf("Infinite loop detected. Terminating.\n\n");
    break;
  case MEMORY_OUT_OF_BOUNDS:
    printf("Attempted Out-of-Bounds Memory Access. Terminating.\n\n");
    break;
  default:
    printf("Unspecified error. Terminating.\n\n");
  }
  printData(cpu,0);
}

void
readObjectFile(cpu_t *cpu, char *fname)
{
  FILE *object_file = fopen(fname, "rb");
  fread(cpu->code, sizeof(word_t), CODESPACE, object_file);
  fclose(object_file);
}

void
readDataFile(cpu_t *cpu, char *fname)
{
  FILE *data_file = fopen(fname, "r");
  word_t *dp = cpu->data;
  while(!feof(data_file)) {
    fscanf(data_file, "%4X", (unsigned int *) dp);
    dp++;
  }
  *(dp-1) = 0xFFFF; // Because my pointer-fu is weak.
  fclose(data_file);
}

int
main(int argc, char **argv)
{
  cpu_t         * cpu          = makeCpu();
  instruction_t * instructions = makeInstructionTable();
  word_t          instruction;
  int             instructions_executed = 0;
  operand_t       opcode, op1, op2;
  
  if (argc != 3) {
    printf("usage: ./sim object_file data_file\n");
    exit(1);
  }

  readObjectFile(cpu, argv[1]);
  readDataFile(cpu, argv[2]);

  while (THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING == 42) {

    // fetch instruction
    if (cpu->pc > CODESPACE) {
      cpu->error = INSTRUCTION_OUT_OF_BOUNDS;
      break;
    }
    
    instruction = cpu->code[cpu->pc];
    
    // parse instruction
    opcode = OPCODE(instruction);
    op1    = OPERAND1(instruction);
    op2    = OPERAND2(instruction);

    if (instructions[opcode] == 0) {
      cpu->error = INVALID_INSTRUCTION;
      break;
    } else if (instructions_executed++ == INT_MAX) {
      cpu->error = INFINITE_LOOP;
      break;
    }

    // execute instruction
    (*instructions[opcode])(cpu, op1, op2);
    if (cpu->error) { break; }
    cpu->pc++;
  }
  printError(cpu);
  return 1; //always an error, I guess...
}
