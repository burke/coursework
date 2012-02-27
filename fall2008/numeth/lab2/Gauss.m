function x = Gauss(a)

u = a

l = u


[R,C] = size(u)

for r = 1:R
  for c = 1:C
    l(r,c) = 0
  end
end

for r = 1:R
  l(r,r) = 1
end

for r = 1:R
  for c = 1:C
    if(r>c)
      m = (u(r,c)/u(c,c))
      
      l(r,c) = m
      
      u(r,1:C) = u(r,1:C) - m*u(c,1:C)
    end
  end
end

disp(l)
disp(u)