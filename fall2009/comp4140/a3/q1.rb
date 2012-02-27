lambda { |sbox| # hooray, closures.
  puts sbox.product(sbox).inject(Array.new(16){Array.new(16){0}}) { |table, xs|
    table.tap{|e|e[ xs[0] ^ xs[1] ][ sbox[xs[0]] ^ sbox[xs[1]] ] += 1}
  }.map(&:inspect)
}["E213D906F45A8C7B".each_char.map{|e|e.to_i(16)}]

=begin expanded, equivalent, more readable, way less fun version.

sbox = "E213D906F45A8C7B".each_char.map{|e|e.to_i(16)}

table = Array.new(16){Array.new(16){0}}

sbox.each do |x1|
  sbox.each do |x2|
    table[x1 ^ x2][sbox[x1] ^ sbox[x2]] += 1
  end
end

puts table.map(&:inspect).join("\n")

=end

=begin Output:
[16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
[0, 0, 2, 0, 4, 0, 2, 0, 0, 0, 0, 2, 4, 0, 0, 2]
[0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 2, 2, 6]
[0, 2, 0, 4, 0, 2, 0, 0, 0, 2, 0, 4, 0, 2, 0, 0]
[0, 4, 2, 2, 0, 2, 0, 2, 2, 0, 0, 2, 0, 0, 0, 0]
[0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 2, 2, 2, 2]
[0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 2, 2, 2]
[0, 0, 4, 2, 2, 0, 0, 0, 4, 2, 0, 0, 0, 0, 2, 0]
[0, 2, 0, 0, 2, 4, 2, 2, 0, 2, 0, 0, 0, 2, 0, 0]
[0, 6, 0, 0, 0, 0, 2, 0, 0, 0, 2, 4, 0, 2, 0, 0]
[0, 0, 2, 0, 0, 0, 0, 2, 4, 0, 4, 2, 0, 0, 2, 0]
[0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 4, 0, 4, 0]
[0, 0, 2, 0, 0, 2, 4, 0, 2, 0, 0, 0, 2, 2, 2, 0]
[0, 0, 2, 2, 2, 0, 2, 0, 0, 2, 6, 0, 0, 0, 0, 0]
[0, 0, 2, 2, 0, 0, 0, 0, 2, 6, 0, 0, 0, 0, 0, 4]
[0, 0, 0, 0, 2, 4, 0, 2, 0, 2, 0, 2, 2, 2, 0, 0]

=end
