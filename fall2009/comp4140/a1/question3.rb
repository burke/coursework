#!/usr/bin/env ruby
# File: question3.rb
# Author: Burke Libbey
# Modified: <2009-10-05 19:18:20 CDT>

def list_of_inverses(n,m=0)
  [(0..n).select{|x|(m*x)%40==1}[0], *list_of_inverses(n,m+1)].compact.sort if m<n
end 

if __FILE__ == $0
  puts list_of_inverses(40).inspect
end
  
# [1, 3, 7, 9, 11, 13, 17, 19, 21, 23, 27, 29, 31, 33, 37, 39]
