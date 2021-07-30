
format long;

a = 1;
b = 3;
val = -1.4260247818;

f = @(x) 100 ./ (x.^2) .* sin(10 ./ x);
x = linspace(1, 3, 100);
eps = 10^(-4);

plot(x, f(x));

adapt = adaptive(f, a, b, eps)
err1 = abs(val - adapt)

is50 = simp(f, a, b, 50)
err1 = abs(val - is50)

is100 = simp(f, a, b, 100)
err1 = abs(val - is100)

