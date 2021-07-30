pkg load symbolic  

x = linspace(-5, 5, 15)
f = @(x) sin (2*x)
ff = @(x) 2 * cos (2*x)
xx = linspace(-5, 5, 100)
##xx = [-6:0.01:6]

ans = HermitePol(x, f(x), ff(x), xx);

hold on

plot(xx, f(xx), "b")
plot(xx, ans, "r")