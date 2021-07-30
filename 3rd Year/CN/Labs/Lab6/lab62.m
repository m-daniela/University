[x, y] = ginput(5)
xx = linspace(min(x), max(x), 100)

sp = spline(x, y)
hold on
plot(x, y, "+")
plot(xx, ppval(sp, xx))