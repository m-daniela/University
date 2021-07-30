n = 4
m = zeros([n, n + 1])
xi = [2 4 6 8]
f = [4 8 14 16]

% add the first 2 given columns to the matrix
for i = 1:n
    m(i, 1) = xi(i)
    m(i, 2) = f(i)
endfor


% compute the divided differences table using
% the formula 
% delta^k f(x_i) = (delta^(k-1) f(x_(i+1)) - delta^(k-1) f(x_i))/(x_(i+k)) - x_i)
for k = 2:n+1
  for i = 1:n+1-k
    m(i, k+1) = (m(i+1, k) - m(i, k))/(xi(i + k -1) - xi(i))
  endfor
endfor

m