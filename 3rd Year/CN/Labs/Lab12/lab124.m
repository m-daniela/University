f = @(x) (x-2) ^ 2 - log(x);
a = 1;
b = 2;
eps = 10^(-4);
N = 100;
nb = 0;
nf = 0;

# bisection

if f(a) * f(b) < 0
  while nb <= N
    nb = nb + 1;
    c = (a + b) / 2;
    if abs(f(c)) < eps
      bis = c
      nb
      return
    endif
    if f(a) * f(c) <= 0
      b = c;
    else
      a = c;
    endif
  endwhile
endif

nb

