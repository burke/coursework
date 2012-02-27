#!/usr/bin/env ruby

# File:     ioc.rb (Question 1)
# Author:   Burke Libbey <burke@burkelibbey.org>
# Modified: <2009-10-20 20:39:52 CDT>

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

def index_of_coincidence(matrix)
  indices_of_coincidence = []

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

(1..5).each do |m|
  matrix = ciphertext.matrix_with_key(m)
  puts "m=#{m}: #{index_of_coincidence(matrix).map(&:to_f).inspect}"
end

