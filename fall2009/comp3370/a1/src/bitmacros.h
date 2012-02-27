// File:     bitmacros.h
// Author:   Burke Libbey
// Modified: <2009-10-08 14:16:41 CDT>

#pragma once

#define THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING 42

#define OPCODE(instr)   (((instr) & 0x00FC) >> 2)
#define OPERAND1(instr) ((((instr) & 0x0003) << 2) | (((instr) & 0xC000) >> 14))
#define OPERAND2(instr) (((instr) & 0x3F00) >> 8)

// Sign extension...
#define SIGN_EXT(n) (((n) & 32) ? ((n) | 0xFFC0) : (n))
