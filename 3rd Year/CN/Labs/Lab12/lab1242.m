# false positive
f = @(x) (x-2) ^ 2 - log(x);
a = 1;
b = 2;
eps = 10^(-4);
N = 100;
nc = 0;


if f(a) * f(b) < 0
  while nc <= N
    nc = nc + 1;
    c = (f(b) * a - f(a) * b) / (f(b) - f(a)); 
    if abs(f(c)) < eps
      false_position = c
      nc
      return
    endif
    if f(a) * f(c) < 0
      b = c;
    else
      a = c;
    endif
  endwhile
endif

nc