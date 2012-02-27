#!/usr/bin/env ruby
# -*- mode: ruby -*-

b=[]
c=[]
current=0
i=42

loop do

  b[i]=false
  continue = false
  while continue == false do
    continue = true
    if current != i
      c[i] = true
      k=i if b[current]
      continue = false
    else
      c[i] = false
      (1..N).each do |j|
        continue = false if (j != i and !c[j])
      end
    end
  end
  
  critical_section

  c[i]=b[i]=true

  non_critical_section
  
end
