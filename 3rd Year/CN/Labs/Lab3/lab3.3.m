
f = @(x) 1 + cos(pi * x)./ (1 + x);
xplot = linspace(0, 10, 100)

# the 21 equidistant nodes
x = linspace(0, 10, 21)

# points in the interval
xx = [0:0.01:10]

rez = LAGRANGE(x, f(x), xx)


hold on
plot(xplot, f(xplot), "g")
plot(xx, rez, "r")
