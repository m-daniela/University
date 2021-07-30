format long
# a. 

a = 1;
b = 1.5;
f = @(x) exp(-x.^2);

i = rec(f, a, b)

# b.

hold on;

fplot(f, [a, b], "g");

mid = (a + b) / 2;

x = [1 1.5 1.5 1 1];
y = [0 0 f(mid) f(mid) 0];
plot(x, y, 'b');


# c.

i1 = reprec(f, a, b, 150)
i2 = reprec(f, a, b, 500)




