#!/usr/bin/env ruby
# -*- coding: utf-8 -*-

# File:     cvigen.rb (Question 2)
# Author:   Burke Libbey <burke@burkelibbey.org>
# Modified: <2009-10-20 20:40:08 CDT>

require 'mathn'

# call "./ioc.rb in.txt"
ciphertext = File.read(ARGV[0]). # Read in an array of lines
  map(&:strip).                   # Remove newlines
  join('').                       # Concatenate into one long string
  gsub(/\s/,'')                   # killify spaces.

def letter_frequencies(letters)

  counts = letters.inject(Hash.new(0)) do |a,v|
    a[v] += 1
    a
  end

end

def ciphertext.matrix_with_key(m)
  Matrix.columns(self.          # Matrix.columns transposes the input
                 each_char.     # Split into a list of component characters... 
                 to_a.          #  ... and make an array of it
                 each_slice(m). # Slice into m-sized chunks [1,2,3,4],m=2 => [[1,2],[3,4]
                 to_a).         #  ... and return an array of it.
    to_a.                       # We only need the matrix for it's transposition
    map(&:compact)              # Get rid of trailing nils in individual rows
end

# Aaaand here's where I quit writing readable code. Good luck.
# Here be dragonnes.
# Abandon all hope, ye who enter here.
# Don't say I didn't warn you.

def index_of_coincidence(matrix)

  matrix.inject([]) do |indices, row_letters|
    frequencies = letter_frequencies(row_letters)

    indices << ("A".."Z").inject(0) { |a,v|
      x = frequencies[v]
      a += x*(x-1)
      a
    } / (row_letters.size * (row_letters.size - 1))
  end

end

class Array
  def average
    self.inject(&:+)/self.size.to_f
  end
end

def guess_m(ciphertext)
  (1..10).each do |m|
    matrix = ciphertext.matrix_with_key(m)
    if index_of_coincidence(matrix).average > 0.06
      return [m, matrix]
    end
  end
end

m,matrix = guess_m(ciphertext)

def shift(x,shift)
  x.map{|e|((((e[0]-?A)+shift)%26)+?A).chr}
end

def mutual_index_of_coincidence(matrix, i, j, g)
  x = matrix[i]
  y = shift(matrix[j], g)

  freq_x = letter_frequencies(x)
  freq_y = letter_frequencies(y)
  
  ("A".."Z").inject(0) { |a,v|
    a += freq_x[v]*freq_y[v]
    a
  } / (x.size * y.size)
end

mutual_indices = []

(0...m).each do |i|
  mutual_indices[i] = []
  (0...m).each do |j|
    mutual_indices[i][j] = (0..25).inject([0,0]) do |a,g|
      x = mutual_index_of_coincidence(matrix,i,j,g).to_f
      if x > a[0]
        a[0] = x
        a[1] = g
      end
      a
    end
  end
end

mutual_indices_matrix = mutual_indices

stats = Hash.new()

(0...m).each do |i|
  (0...m).each do |j|
    v = mutual_indices_matrix[i][j]
    stats[v[0]] = [i,j,v[1]]
  end
end


# strongest evidence first
ijg = stats.keys.sort.reverse.map{|k|stats[k]}

key = Array.new(m)

key[0] = 0

while key[0...m].include?(nil)
  ijg.each do |v|
    if key[v[0]] && !key[v[1]]
      # k_v1 = k_v0 - v2
      key[v[1]] = (key[v[0]] - v[2])%26
      break
    elsif key[v[1]] && !key[v[0]]
      # k_v0 = v2 + k_v1
      key[v[0]] = (v[2] + key[v[1]])%26
      break
    end
  end
end    

def decrypt(ciphertext,key)
  matrix = ciphertext.matrix_with_key(key.size)
  matrix.each_with_index do |row,index|
    matrix[index] = shift(row, -1*key[index])
  end

  return Matrix.columns(matrix).to_a
end

best_decryption = [0,0,0]

(0..25).each do |n|
  this_key = key.map{|e|(((e+n)%26)+?A).chr}.join('')
  this_plaintext = decrypt(ciphertext, key.map{|e|(e+n)%26}).join('')
  this_e_count = this_plaintext.scan(/E/).size
  if this_e_count > best_decryption[0]
    best_decryption = [this_e_count, this_key, this_plaintext]
  end
end

puts "key: #{best_decryption[1]}"
puts "plaintext: #{best_decryption[2]}"
# QED, etc.

#plaintext is:

=begin

At the same time, patients and their families have become increasingly
involved — and influential — in all aspects of medical care. In the
mid-eighties, as the first anti-viral drugs for treating AIDS were being
developed, activists demanded to participate in the design of clinical
trials directed by the National Institutes of Health and pharmaceutical
companies. Inspired by the activists’ example, breast-cancer patient-advocacy
groups made similar requests. The AIDS groups interrupted meetings and
staged “die-ins” at the N.I.H., and, eventually, the physicians in charge
of planning the clinical trials agreed to their demands. Laypeople now
routinely sit on committees at the N.I.H. and on hospitals’ institutional
review boards, which assess the ethicality and scientific merit of clinical
trials, particularly those involving experimental drugs or procedures.

(http://www.jeromegroopman.com/articles/being-there.html)

=end
