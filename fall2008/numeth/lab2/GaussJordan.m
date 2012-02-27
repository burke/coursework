function x = GaussJordan(ma,mb)

a = [ma,mb]

[R,C] = size(a)

for r = 1:R
  for c = 1:C
    a(c,:) = a(c,:)/a(c,c) %Normalize the pivot row wrt this col.
    
    m = a(r,c)/a(c,c)

    for x = 1:R
      if x ~= r
        for y = 1:C
          a(x,y) = a(x,y) - m*a(c,y) 
        end
      end
    end
  end
end