% 2. a

hold on

% define the first 3 polynomials
T1=@(t) cos(1*acos(t))
T2=@(t) cos(2*acos(t))
T3=@(t) cos(3*acos(t))


% plot them
##t = -1:0.1:1
t = linspace(-1,1,1000);
plot(t, T1(t))
plot(t, T2(t))
plot(t, T3(t))
title('First 3 Chebyshev polynomials')




