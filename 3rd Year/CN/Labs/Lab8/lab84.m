
a = 1;
b = 2;

eps = 10 ^ 3;

# fix returns the integer part of the number
r = fix(0.636294368858383 * eps);

f = @(x) x.*log(x);

n = 1;
int = trap(f, a, b, n);

while fix(int * eps) != r
  n = n + 1;
  int = trap(f, a, b, n);
endwhile

int
n
