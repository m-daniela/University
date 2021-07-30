% i = 0, 6

h = 0.25
f = @(x) sqrt(5 * x.^2 + 1)
n = 6
m = zeros([n n+2])

% compute first two columns
% xi, i = 0, 6
% f(xi)
for i = 0:n
  xi = 1 + i * h
  m(i+1, 1) = xi
  m(i+1, 2) = f(xi)
endfor

% compute the finite differences table using
% the formula 
% delta^k f(x_i) = (delta^(k-1) f(x_(i+1)) - delta^(k-1) f(x_i))
for k = 2:n+2
  for i = 1:n+2-k
    m(i, k+1) = m(i+1, k) - m(i, k)
  endfor
endfor

m