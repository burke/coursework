#!/usr/bin/env ruby

# File:     question3.rb
# Author:   Burke Libbey <burke@burkelibbey.org>
# Modified: <2009-10-20 21:08:11 CDT>

require 'mathn'

# a) Fill a matrix column by column, then read row by row.
def decrypt(str, m, n)
  letters = str.each_char.to_a
  decrypted = ""
  letters.each_slice(m*n) do |sub|
    matrix = Matrix.columns(sub.each_slice(n).to_a)
    decrypted << matrix.to_a.join('')
  end
  return decrypted
end

if __FILE__ == $0
  (1..5).each do |m|
    (1..5).each do |n|
      print decrypt("MYAMRARUYIQTENCTORAHROYWDSOYEOUARRGDERNOGW",m,n)
      puts " : #{m} #{n}"
    end
  end
end

# MARYMARYQUITECONTRARYHOWDOESYOURGARDENGROW : 3 2
