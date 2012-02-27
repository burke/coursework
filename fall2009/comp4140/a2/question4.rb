#!/usr/bin/env ruby

# File:     question4.rb
# Author:   Burke Libbey <burke@burkelibbey.org>
# Modified: <2009-10-20 20:40:42 CDT>

# K*[[a b] [b c]] = [[E F] [A B]]
# K * M1 = M2
# K = M2 * M1^-1

require 'mathn'

def c(char)
  char-?A
end

m1 = Matrix[[c(?A), c(?B)], [c(?B), c(?C)]]
m2 = Matrix[[c(?E), c(?F)], [c(?A), c(?B)]]

k = m2 - m1.inverse

puts k.to_a.map(&:inspect)

# [6, 4]
# [-1, 1]

