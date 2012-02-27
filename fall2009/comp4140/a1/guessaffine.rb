
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

If T isn't in the top 3, I'll assume it's 4th. M = T.
E is almost certainly first... P = E.
PA is a common digraph, so it's probably ER. A = R.
DA is also common, and D is quite common, so DA = OR => D = O.
MOV is a common trigraph, and M is T, so O=H and V=E
  - P is not E.
  - P is O.
    - D is not O.

You know what, I'm pretty sure I have everything wrong. I already solved 7.
Calling this a night.

=end
