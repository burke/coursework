# file: lib/node.rb

# Implements a node in a potentially-doubly-linked list.
class Node

  attr_accessor :data, :nxt, :prv
  
  def initialize(data, nxt, prv=nil)
    @data,@nxt,@prv = data,nxt,prv
  end
  
end
