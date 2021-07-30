
a = 0;
b = pi;
n1 = 10;
n2 = 30;

f = @(x) 1 ./ (4 + sin(20 .* x));

int1 = simp(f, a, b, n1)
int2 = simp(f, a, b, n2)