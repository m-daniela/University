
x = [0, pi/2, pi, 3*pi/2, 2*pi];
y = sin(x);
xx = pi/4;
val = sin(xx)
dom = linspace(0, 2*pi, 100);

# method 1
##spline(x, y, xx)

# method 2 
natspl = spline(x, y);
natspl2 = ppval(spline(x, y), xx)

# deriv: cos(x)
clamp = [1, y, 1];
clampspl = spline(x, clamp);
clampspl2 = ppval(clampspl, xx)

hold on
plot(x, y, "+")
plot(dom, sin(dom), "r")
plot(dom, ppval(natspl, dom), "b")
plot(dom, ppval(clampspl, dom), "g")
