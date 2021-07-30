x = [1 2 3 4 5]
f = [22 23 25 30 28]
xx = [2.5]

pol = NewtonPol_stud(x, f, xx)

xx = [0:0.01:5]
pol = NewtonPol_stud(x, f, xx)

hold on
plot(x, f, "+")
plot(xx, pol)