x = [0, 10, 20, 30, 40, 60, 80, 100];
f = [0.0061, 0.0123, 0.0234, 0.0424, 0.0738, 0.1992, 0.4736, 1.0133];
T = 45;
P = 0.095848;

# a

p1 = polyfit(x, f, 5)
v1 = polyval(p1, T)

p2 = polyfit(x, f, 2)
v2 = polyval(p2, T)

e1 = abs(P - v1)
e2 = abs(P - v2)

# b

xx = [0:0.1:100];

hold on;
plot(x, f, "+");
plot(xx, polyval(p1, xx), "g");
plot(xx, polyval(p2, xx), "b");
