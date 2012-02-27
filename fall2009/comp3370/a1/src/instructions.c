// File:     instructions.c
// Author:   Burke Libbey
// Modified: <2009-10-11 12:22:04 CDT>

#include <stdlib.h>
#include <stdio.h>

#include "bitmacros.h"
#include "instructions.h"

#define REG(n)        (cpu->reg[(n)])
#define REG_FROM_2(n) (cpu->reg[(n)>>2])

#define MEM_SET(arg1, arg2)              \
  do {                                   \
    cpu->mar = REG(arg1);                \
    cpu->mdr = arg2;                     \
    if (cpu->mar > DATASPACE) {          \
      cpu->error = MEMORY_OUT_OF_BOUNDS; \
      break;                             \
    }                                    \
    cpu->data[cpu->mar] = cpu->mdr;      \
  } while (0)

#define MEM_GET(arg1, arg2)              \
  do {                                   \
    cpu->mar = arg1;                     \
    cpu->mdr = cpu->data[cpu->mar];      \
    if (cpu->mar > DATASPACE) {          \
      cpu->error = MEMORY_OUT_OF_BOUNDS; \
      break;                             \
    }                                    \
    (arg2) = cpu->mdr;                   \
  } while (0)

#define ARITHMETIC(op1, op2, operator)          \
  do {                                          \
    REG(op1) = (REG(op1) operator (op2));       \
  } while (0)

#define BRANCH_ON_CONDITION(condition)          \
  do {                                          \
    if (REG(op1) condition REG(0))              \
      cpu->pc += SIGN_EXT(op2) - 1;             \
  } while (0)

// Define a function to implement this instruction, then put it
//   in the instructions array.
#define INSTRUCTION(opcode, name, body)          \
  void                                           \
  name(cpu_t *cpu, operand_t op1, operand_t op2) \
  {                                              \
    body;                                        \
  }                                              \
  instructions[opcode] = name

instruction_t *
makeInstructionTable()
{
  instruction_t *instructions = calloc(64, sizeof(instruction_t));

  /* Arithmetic instructions *************************************/
  INSTRUCTION(0x00, ADD_L,   ARITHMETIC(op1,   SIGN_EXT(op2), +) );
  INSTRUCTION(0x01, ADD_R,   ARITHMETIC(op1, REG_FROM_2(op2), +) );
  INSTRUCTION(0x08, SUB_L,   ARITHMETIC(op1,   SIGN_EXT(op2), -) );
  INSTRUCTION(0x09, SUB_R,   ARITHMETIC(op1, REG_FROM_2(op2), -) );
  INSTRUCTION(0x10, AND_L,   ARITHMETIC(op1,   SIGN_EXT(op2), &) );
  INSTRUCTION(0x11, AND_R,   ARITHMETIC(op1, REG_FROM_2(op2), &) );
  INSTRUCTION(0x18, OR_L,    ARITHMETIC(op1,   SIGN_EXT(op2), |) );
  INSTRUCTION(0x19, OR_R,    ARITHMETIC(op1, REG_FROM_2(op2), |) );
  INSTRUCTION(0x20, XOR_L,   ARITHMETIC(op1,   SIGN_EXT(op2), ^) );
  INSTRUCTION(0x21, XOR_R,   ARITHMETIC(op1, REG_FROM_2(op2), ^) );
  /* Move instructions *******************************************/
  INSTRUCTION(0x28, MOV_L_R, REG(op1) = SIGN_EXT(op2)            );
  INSTRUCTION(0x29, MOV_L_M, MEM_SET(op1, op2)                   );
  INSTRUCTION(0x2A, MOV_R_R, REG(op1) = REG_FROM_2(op2)          );//non-spec
  INSTRUCTION(0x2C, MOV_M_R, MEM_GET(REG_FROM_2(op2), REG(op1))  );
  INSTRUCTION(0x2D, MOV_R_M, MEM_SET(op1, REG_FROM_2(op2))       );
  /* Shift instructions ******************************************/
  INSTRUCTION(0x30, SRR,     ARITHMETIC(op1, 1, <<)              );
  INSTRUCTION(0x31, SRL,     ARITHMETIC(op1, 1, >>)              );
  /* Branch instructions *****************************************/
  INSTRUCTION(0x38, JR,      cpu->pc = op1-1                     );
  INSTRUCTION(0x39, BEQ,     BRANCH_ON_CONDITION(==)             );
  INSTRUCTION(0x3A, BNE,     BRANCH_ON_CONDITION(!=)             );
  INSTRUCTION(0x3B, BLT,     BRANCH_ON_CONDITION(<)              );
  INSTRUCTION(0x3C, BGT,     BRANCH_ON_CONDITION(>)              );
  INSTRUCTION(0x3D, BLE,     BRANCH_ON_CONDITION(<=)             );
  INSTRUCTION(0x3E, BGE,     BRANCH_ON_CONDITION(>=)             );

  return instructions;
}

