format long;

f = @(x) 2./(1 + x.^2);
a = 0;
b = 1;
eps = 10^(-4);

h = b - a;

# first term
qt0 = h / 2 * (f(a) + f(b));
i = romberg(f, qt0, 1, a, b, eps)
