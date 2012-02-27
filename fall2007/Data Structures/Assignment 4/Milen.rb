#!/usr/bin/env ruby

class Milen

  def initialize
    @data = Hash.new
  end

  def evaluate expression
    # get an array of operators and operands in the RHS
    rhs = expression.split('=')[1].split(' ')
    
    for op in rhs
      if op.match /^[\d]+$/
        #nothing
      elsif %w{+,-,*,/}.index op
        #nothing
      elsif

    end
    
  end

end

milen = Milen.new

milen.evaluate gets
