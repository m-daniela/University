f = @(x) x^3 - x^2 - 1;
eps = 10^(-4);
N = 100;
x0 = 1;
x1 = 2;

for n = 1:N
  xn = x1 - (x1 - x0)/(f(x1) - f(x0)) * f(x1);
  if abs(xn - x1) < eps
    res = xn
    n
    return
  endif
  x0 = x1
  x1 = xn
endfor

printf("Not enought iterations")
n
  