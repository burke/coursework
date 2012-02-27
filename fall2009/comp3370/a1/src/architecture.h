// File:     architecture.h
// Author:   Burke Libbey
// Modified: <2009-10-11 12:14:19 CDT>

#pragma once

#include <stdint.h>

#define N_REGISTERS 16

#define CODESPACE 1024
#define DATASPACE 1024

typedef enum errorCode { OK = 0,
                         INSTRUCTION_OUT_OF_BOUNDS,
                         INVALID_INSTRUCTION,
                         INFINITE_LOOP,
                         MEMORY_OUT_OF_BOUNDS } errorCode;

typedef uint16_t word_t;
typedef uint8_t  operand_t;
typedef uint16_t instr_return_t;

typedef struct cpu {
  word_t reg[N_REGISTERS];
  word_t pc;
  word_t mar;
  word_t mdr;
  word_t *code;
  word_t *data;
  errorCode error;
} cpu_t;

cpu_t *makeCpu();
