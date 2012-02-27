#!/usr/bin/env ruby

# File:     question5.rb
# Author:   Burke Libbey <burke@burkelibbey.org>
# Modified: <2009-10-20 20:41:04 CDT>

class String
  def zipWith(other, method)
    x = self.upcase.each_char.to_a.map{|e|e[0]-?A}
    y = other.upcase.each_char.to_a.map{|e|e[0]-?A}
    z = x.zip(y).map{|e|e[0].send(method, e[1])}
    z.map{|e|((e%26)+?A).chr}.join('')
  end
end

class Array
  def rotate!
    self.push(self.shift)
  end
end

p = "washingtonDC"
c = "UDQZWXQEWFCB"

k = c.zipWith(p, :-)
k *= 8 # maybe it just repeats?

c2 = "FGHIPCQXZZERTLTKVWAFNVYUTWWWWDTKSFFNMBGEQWASOPLJIHGTNMBGR"

12.times do
  p2 = c2.zipWith(k, :-)
  puts p2
  k=k.split('').rotate!.join('')
end

#=> HOTYFRIFAAGOVTFALLSNOWARVEIMMSLSTGHKOJSUGLSAPQNGKPSJDBTOS
#   ----------++++++++++++-----------------------------------
# Gov't Falls Now.
