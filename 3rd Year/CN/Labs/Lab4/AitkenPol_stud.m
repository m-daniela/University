function z = AitkenPol_stud(x,y,a)
er=1e-1; 
[z, I] = sort(abs(x-a));
# sort the element i.e. closer to 115
x = x(I);
y = y(I);
n=length(x)

ak=zeros(n,n);
ak(:,1)=y';

# generate the Aitken table using the determinant formula
for i=2:n
    for j=1:i-1
       ak(i, j+1) = 1 / (x(i) - x(j)) .* det([ak(j, j), x(j) - a; ak(i, j), x(i) - a])
    end
      # if the stopping condition is fulfilled, stop the algorithm
      if abs(ak(i, i) - ak(i-1, i-1)) <= er
        z = ak(i, i)
        return
      end
end

