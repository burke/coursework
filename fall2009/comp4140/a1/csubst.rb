require 'pp'

class Array
  def move(el, offset)
    ix = self.index(el)
    self.delete(el)
    self.insert(ix+offset, el)
    self
  end
end

fin = File.read('csubst.txt')

hsh = Hash.new(0)

freq = ["e", "t", "a", "o", "i", "n", "s", "h", "r", "d", "l", "c", "u", "m", "w", "f", "g", "y", "p", "b", "v", "k", "j", "x", "q", "z"]

ARGV.each do |opt|
  char = opt[0].chr
  op   = opt[1].chr
  num  = opt[2..-1].to_i
  num *= -1 if op == "+"

  freq = freq.move(char, num)
end

puts freq.inspect

fin.gsub!(/\s+/,'')

fin.each_char do |c|
  hsh[c] += 1
end

ha = hsh.to_a.sort_by{|e|e[1]}.reverse

ha.each_with_index do |x,i|
  x[2] = freq[i]
end

nhsh = {}

ha.each do |z|
  nhsh[z[0]] = z[2]
end

fin.each_char do |c|
  print nhsh[c]
end
