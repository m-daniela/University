
% 3. 
hold on
x = linspace(-1, 3, 100)

% separate case for first degree
f = @(x) ones(size(x))

plot(x, f(x))

% the rest of the polynomoials
% simplified, since we know that x0 = 0
for k = 1:6
  f = @(x) f(x) + (x.^k) / factorial(k)
  plot(x, f(x))
endfor

plot(x, @exp(x))
