#!/usr/bin/env ruby

require 'mathn'

class WPGMA
  def initialize(distances)
    @distances = distances
    @labels = ("A".."Z").to_a[0...(@distances.size)]
  end

  def to_s
    calculate_tree(@distances, @labels)
  end

  def calculate_tree(matrix, labels)

    return labels if matrix.size < 2

    # first, find the minimum value...
    min,min_i,min_j=nil
    (1...matrix.size).each do |i|
      (0...i).each do |j|
        if !min or min > matrix[i][j]
          min = matrix[i][j]
          min_i = i
          min_j = j
        end
      end
    end
    # so now we want to merge nodes min_i and min_j.
    label1 = labels.delete_at(min_i)
    label2 = labels.delete_at(min_j)
    labels << "(#{label1}#{label2})"

    # Keep all the data that we're not merging 
    nodes_to_keep = (0...matrix.size).reject{|e|e==min_i || e==min_j}

    
    new_size = matrix.size-1
    new_mx = Array.new(new_size){Array.new(new_size)}

    # fill in the data that we're just copying over
    (new_size-1).times do |i|
      (new_size-1).times do |j|
        new_mx[i][j] = matrix[nodes_to_keep[i]][nodes_to_keep[j]]
      end
    end

    # Figure out the averaged row and column
    nodes_to_keep.size.times do |i|
      x = nodes_to_keep[i]
      d_i = matrix[min_i][x]
      d_j = matrix[min_j][x]
      new_mx[new_size-1][i] = 0.5*((d_i)+(d_j))
      new_mx[i][new_size-1] = 0.5*((d_i)+(d_j))
    end
    new_mx[new_size-1][new_size-1] = 0

    # RECURSE!
    return calculate_tree(new_mx, labels)
    
  end
  
end

if __FILE__ == $0
  rows = []
  while line = gets
    rows << line.split(/\s+/).map(&:to_f)
  end

  puts WPGMA.new(rows).to_s
end
