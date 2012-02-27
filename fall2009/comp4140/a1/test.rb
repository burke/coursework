class Array
  def encrypt(a,b)
    self.map{|x| (a*x + b)%27}
  end
end
  
(0..26).each do |a|
  (0..26).each do |b|
    x = (0..26).to_a
    
    #if x.encrypt(a,b).encrypt(a,b) == x
    #  puts "#{a},#{b}"
    #end
  end
end

x = (0..26).to_a
puts x.encrypt(26,0).inspect
