
% 1.
% define the functions
l1 = @(x) x
l2 = @(x) (3/2*x.^2 - 1/2)
l3 = @(x) (5/2*x.^3 - 3/2.*x)
l4 = @(x) (35/8*x.^4 - 15/4.*x.^2 + 3/8)

% plot each function on [0, 1]
x = linspace(0, 1, 100)

subplot(2,2,1)
plot(x, l1(x))
title('Plot f1')

subplot(2,2,2)
plot(x, l2(x))
title('Plot f2')

subplot(2,2,3)
plot(x, l3(x))
title('Plot f3')

subplot(2,2,4)
plot(x, l4(x))
title('Plot f4')
















