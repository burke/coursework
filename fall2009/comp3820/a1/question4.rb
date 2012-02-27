

filename       = ARGV[0]
start_penalty  = ARGV[1].to_i
length_penalty = ARGV[2].to_i
$seq1           = ARGV[3]
$seq2           = ARGV[4]

p = -2

score_mx = File.read(filename).map{|e|e.strip.split(/\s*\|\s*/)}

$scores = {}

(1...score_mx.size).each do |n|
  d = score_mx[0].zip(score_mx[n])
  $scores[d[0][1]] = d[1..-1].inject({}) do |h,v|
    h[v[0]] = v[1].to_i
    h
  end
end

m = Array.new($seq2.size){Array.new($seq1.size){0}}
m[0][0] = 0

(0...m[0].size).each do |i|
  m[0][i] = -2 * i #FIXME
end

(0...m.size).each do |j|
  m[j][0] = -2 * j #FIXME
end

def a(i,j)
  $scores[$seq1[i].chr][$seq2[j].chr]
end

(1...m.size).each do |j|
  (1...m[j].size).each do |i|
    opt_match = m[j-1][i-1] + a(i,j)
    opt_gx    = m[j-1][i]   + (-2)
    opt_gy    = m[j][i-1]   + (-2)
    m[j][i] = [opt_match,opt_gx,opt_gy].max
  end
end

j = m.size-1
i = m[0].size-1

align_x = ""
align_y = ""

while j > 0 and i > 0
  if m[j-1][i-1] + a(i,j) == m[j][i]
    align_x << $seq1[i].chr
    align_y << $seq2[j].chr
    i -= 1
    j -= 1
  elsif m[j-1][i] + p == m[j][i]
    align_x << "-"
    align_y << $seq2[j].chr
    j -= 1
  else #if m[j][i-1] + p == m[j][i]
    align_x << $seq1[i].chr
    align_y << "-"
    i -= 1
  end
end

align_x << $seq1[0].chr
align_y << $seq2[0].chr

while j > 0
  align_x << "-"
  j -= 1
end

while i > 0
  align_y << "-"
  i -= 1
end

puts align_x.reverse
puts align_y.reverse
