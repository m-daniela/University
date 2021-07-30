f = @(x) x - cos(x);
ff = @(x) 1 + sin(x);
x0 = pi / 4;
eps = 10^(-4);
N = 100;

for n = 1:N
  xn = x0 - f(x0) / ff(x0);
  if abs(xn - x0) < eps
    res = xn
    n
    return
  endif
  x0 = xn;  
endfor

printf("Not enought iterations")
n