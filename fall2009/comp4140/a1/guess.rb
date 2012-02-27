
ctext = File.read(ARGV[0])

from = ARGV[1]
to   = ARGV[2]

mappings = Hash.new()
from.each_char.zip(to.each_char.zip).each do |row|
  mappings[row[0].upcase] = row[1][0].upcase
end

mappings[' '] = ' '

ctext.each_line do |line|
  puts line
  puts line.each_char.map{|c|mappings[c]||'_'}.join('')
  puts
end

=begin

Process to solve:

OGC has the highest frequency, is obviously THE
FJH is obviously AND.
T is the most common single letter unidentified. most likely O.
P is also very common. Quite likely to be I, R, or S.
P appears twice consecutively in a few places though, so it must be S.
___HANDSH_ -- H___ is a new word, I think, and the first _ must be a vowel.
  It's either I or U.
_N shows up a lot, so I'll go with I.
Now Q is obviously R.
M is K.
Y is V.
S is P.
X is U.
D is D (who'da thunk it)
L is L (wut.)
V is Y.
E is C.
Thought that looked wrong. H is actually G, not D, and F is I, not A.
K is M.
A is B.
Z is U.

Plaintext is:

HOOKER'S RESERVES DID FINALLY CROSS THE RIVER MARCHING SHAKILY ACROSS THE BOUNCING PONTOONS AND THROUGH THE BURNING AND SHATTERED TOWN FORMING THEIR LINES AT THE EDGE OF THE OPEN FIELD. IT WAS LATE AFTERNOON AND SUMNER'S ATTACK HAD RUN ITS COURSE. STEADY STREAMS OF BLOODIED AND HOBBLED MEN NOW CROSSED THE FIELD TOWARD THEM, MANY PASSING RIGHT THROUGH THE LINES WITHOUT SPEAKING, OTHERS CURSING THEIR OWN LUCK OR WARNING THE FRESH TROOPS WHAT AWAITED THEM. OUT THERE BEYOND THE LOW RAISE, CHAMBERLAIN DID NOT WATCH THEM, KEPT HIS EYES TO THE FRONT, STARED OUT ACROSS THE SMOKY PLAIN TOWARD THE HALF HIDDEN HILLS. THE STEADY ROAR OF THE MUSKETS, THE CONSTANT POUNDING OF THE BIG GUNS. (BAED)

=end
