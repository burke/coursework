;;; File:     bubblesort.asm
;;; Author:   Burke Libbey <burke@burkelibbey.org>
;;; Modified: <2009-11-29 22:01:18 CST>
;;;
;;; Sorts a pre-defined number of items on disk using the fastest
;;; sorting algorithm of all time. For serious.
;;;
;;; Sorts from highest to lowest, so that it actually does something
;;; on my fibonacci numbers example.

        MOVE R10,0              ; It's convenient to have access
        MOVE R11,1              ;  to these constants I guess.
        MOVE R12,2
        MOVE R13,3
        MOVE R14,4
        MOVE R15,5

        MOVE R7,23              ; Last element. Size of data-1.
        MOVE R6,0               ; Current sorting progress. Finish when R6=R7

        MOVE R1,22              ; Start sorting at 23 and 22
        MOVE R2,23
        
        ;; Grab these elements from disk, and store them in mem[3],mem[4].
loop:   MOVE [R11],R1           ; Use HD Addr specified in R1
        MOVE [R12],R13          ; Use mem[3]
        MOVE [R10],R12          ; Read one word
        MOVE [R11],R2           ; Use HD Addr specified in R2
        MOVE [R12],R14          ; Store it to mem[4]
        MOVE [R10],R12          ; Read one word again.
        ;; So now HD[R1] and HD[R2] are in mem[3] and mem[4] respectively.
        MOVE R0,[R13]           ; Load mem[3] into a register
        MOVE R5,[R14]           ; ...and mem[4] too.
        BLT  R5,xswap
        ;; Now swap the values if necessary.
        ;; Move mem[3] to HD[R2] and mem[4] to HD[R1].
        MOVE [R11],R1           ; Use HD[R1]
        MOVE [R12],R14          ; Use mem[4]
        MOVE [R10],R13          ; Write one word

        MOVE [R11],R2           ; Use HD[R2]
        MOVE [R12],R13          ; Use mem[3]
        MOVE [R10],R13          ; Write one word

        ;; Ok, either we've swapped the values, or skipped, assuming they're
        ;; already in the correct order. We need to update the comparison
        ;; indices and branch to loop, or terminate if we're fully sorted.
xswap:  SUB  R1,1
        SUB  R2,1 
        ;; If R2 == R6, the current sorting progress, increment R6 and
        ;;   start again at MAX (23)
        MOVE R0,0
        OR   R0,R2
        BNE  R6,xrst
        ;; reset...
        ADD  R6,1
        MOVE R1,22
        MOVE R2,23
        ;; If sort progress == 23, terminate. otherwise, loop.
xrst:   MOVE R0,22
        BNE  R6,loop
