
r = 110;
p = 75;

a = 0;
b = 2*pi;

f = @(x)sqrt(1 - (p/r) ^ 2 * sin(x)); 

n1 = 3;
n2 = 2;

int1 = trap(f, a, b, n1);
int2 = trap(f, a, b, n2);

H2 = 60 * r / (r^2 - p^2) * int2
H3 = 60 * r / (r^2 - p^2) * int1


