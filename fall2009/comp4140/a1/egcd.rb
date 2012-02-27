#!/usr/bin/env ruby
# File: egcd.rb
# Author: Burke Libbey
# Modified: <2009-10-05 19:20:22 CDT>

def ext_euclid(a, b)
  if (a%b).zero?
    return [0, 1]
  else
    x, y = ext_euclid(b, a%b)
    return [y, x-y*(a/b)]
  end
end
      
if __FILE__ == $0
  puts ext_euclid(13, 105)
end
