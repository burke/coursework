        MOVE R2,0
        MOVE R2,[R2]
        MOVE R3,1
loop:   MOVE R5,[R3]
        MOVE R4,[R2]
        MOVE [R2],R5
        MOVE [R3],R4
        ADD  R3,1
        SUB  R2,1
        BGT  R3,loop
