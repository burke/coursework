// File:     instructions.h
// Author:   Burke Libbey
// Modified: <2009-10-08 00:33:38 CDT>

#pragma once

#include "architecture.h"

typedef void (*instruction_t)(cpu_t *cpu, operand_t op1, operand_t op2);

instruction_t *makeInstructionTable();

