a = 0;
b = 0.5;

n1 = 4;
n2 = 10;
r = 0.520499876;

f = @(x) e .^ (-x.^2);

int1 = 2/sqrt(pi) * simp(f, a, b, n1)
er1 = abs(int1 - r)

int2 = 2/sqrt(pi) * simp(f, a, b, n2)
er2 = abs(int2 - r)