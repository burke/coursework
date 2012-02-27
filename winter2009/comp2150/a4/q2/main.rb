#!/usr/bin/env ruby
# -*- mode: ruby -*-

# * NAME            : Burke Libbey
# * STUDENT NUMBER  : 6840752
# * COURSE          : COMP 2150 Object Orientation
# * INSTRUCTOR      : Anderson
# * ASSIGNMENT      : 4, Question 2
#
# REMARKS: A simple ordered binary tree
#
# INPUT: Arbitrary DSL used in the assignment description.
# defined in-file in main.rb
#
# OUTPUT: Depends what the input specifies


$LOAD_PATH << File.join(File.dirname(__FILE__),'lib')

require 'logger'
require 'tree'

($log = Logger.new($stdout)).level = Logger::ERROR

class Question3
  # This hash returns key if no value is found for hash[key]
  @@method_map = Hash.new{|h,k|k}.merge({
      'toString' => 'to_s',
      'Total'    => 'total'
  })

  # Read in data in the nice DSL that Anderson probably unwittingly defined,
  # then run it. Log an error if the line doesn't match the syntax, but continue anyway.
  def self.run(src)
    binding = Kernel.binding
    src.each do |command|
      action = case command
      when /Create a new empty tree (\S+)/        then "#{$1} = Tree.new"
      when /Insert the result of (.+) into (\S+)/ then "#{$2}.insert(#{eval($1)})"
      when /Insert(?: new node)? (.+) into (\S+)/ then "#{$2}.insert(#{$1})"
      when /Print the (\S+) of (\S+)/             then "puts #{$2}.#{@@method_map[$1]}"
      else
        $log.error("Invalid command syntax: #{command}")
        next
      end
      $log.debug action
      eval action, binding
    end
  end
end

if __FILE__ == $0
  Question3.run(DATA)
end

__END__
Create a new empty tree t1
Insert new node 4 into t1
Insert new node 0.4 into t1
Insert new node "bottle" into t1
Insert new node 470 into t1
Print the toString of t1
Print the total of t1
Create a new empty tree t2
Insert the result of 4.0/3 into t2
Insert "Whoa Dude" into t2
Insert "Where's My Car?" into t2
Print the toString of t2
Print the total of t2
Create a new empty tree t3
Print the toString of t3
Print the total of t3
Insert new node t3 into t2 #tree t3 is now a piece of data in t2, not a subtree
Print the toString of t2
Print the total of t2
Insert new node "Tomayto" into t2
Print the toString of t2
Print the total of t2
Insert new node "Tomahto" into t2
Insert new node t2 into t1 #tree t2 is now a piece of data in t1, not a subtree
Print the toString of t1
Print the total of t1
