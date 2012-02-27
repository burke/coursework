;;; File:     fibonacci.asm
;;; Author:   Burke Libbey <burke@burkelibbey.org>
;;; Modified: <2009-11-30 12:37:44 CST>
;;; 
;;; Generates Fibonacci numbers on hard drive
;;; Each number is written in sequence to a new location on the drive
        
        MOVE R10,0              ; HD Status
        MOVE R11,1              ; HD Addr Reg
        MOVE R12,2              ; HD MAR
        MOVE R13,3              ; First real memory loc.
        MOVE R14,4              ; Second memory loc.
        ;; First, put the initial condition in memory.
        MOVE [R13],1            ; Set mem[3] to 1
        MOVE [R14],1            ; Set mem[4] to 1
        ;;  Copy initial conditions for fibs to the HDD.
        MOVE [R11],0            ; Use HD Address 0
        MOVE [R12],3            ; Use memory address 3
        MOVE [R10],3            ; Write one word to HDD
        MOVE [R11],1            ; Now use HD Address 1
        MOVE [R10],3            ; And write another word.
        ;; We now have 1 1 on the HDD. From here, we'll generate
        ;; the fibonacci numbers iteratively, storing them to the
        ;; drive as we go.
        MOVE R0,2               ; HD Address of the next number to generate
        MOVE R8,24              ; Maximum HD Address to generate number for
        ;; So mem[3] and mem[4] contain the last numbers.
        ;; Set mem[3] to mem[4] and mem[4] to mem[3]+mem[4],
        ;; Then write the new mem[4] to the hard drive.
loop:   MOVE R3,[R13]
        MOVE R4,[R14]
        ADD  R3,R4              ; New number
        MOVE [R13],R4
        MOVE [R14],R3
        ;; So mem[3] and mem[4] contain the most recent tail of the seq.
        ;; We need to update the HDD with mem[4].
        MOVE [R11],R0           ; Use next open HDD addr
        MOVE [R12],4            ; Use mem[4].
        MOVE [R10],3            ; Write one word
        ADD  R0,1               ; Set the next address to write a number to
        BGT  R8,loop            ; If we're not done yet, keep going...
        