# the interpolation points
x = linspace(0, 6, 13)
f = @(x) exp(sin(x))
y = f(x)
xx = linspace(0, 6, 200)

# compute the polynomial
pol = NewtonPol_stud(x, y, xx)

hold on

plot(x, y, "+")
plot(xx, f(xx))
plot(xx, pol)

