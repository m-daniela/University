
x = [0 : pi/4 : 2*pi];

xx = linspace(0, 2*pi, 100);
linspl = interp1(x, cos(x), xx);

hold on

plot(xx, cos(xx))
plot(xx, linspl)