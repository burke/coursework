

ctext = File.read("caffine.txt").gsub(/\s/m,'')

freq1 = Hash.new(0)
freq2 = Hash.new(0)
freq3 = Hash.new(0)

prev1 = nil
prev2 = nil
ctext.each_char do |curr|
  freq1[curr] += 1
  (freq2["#{prev1}#{curr}"] += 1) if prev1
  (freq3["#{prev2}#{prev1}#{curr}"] += 1) if prev2

  prev2 = prev1
  prev1 = curr
end

puts "most common letters:"
puts freq1.sort_by{|k,v|v}.reverse.map{|row|"#{row[0]}: #{row[1]}\n"}

puts "most common digraphs:"
puts freq2.sort_by{|k,v|v}.reverse[0..10].map{|row|"#{row[0]}: #{row[1]}\n"}

puts "most common trigraphs:"
puts freq3.sort_by{|k,v|v}.reverse[0..10].map{|row|"#{row[0]}: #{row[1]}\n"}
