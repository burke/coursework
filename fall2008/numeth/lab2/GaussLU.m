function x = GaussLU(a)

u = a

[R,C] = size(u)

l = eye(R,C)

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