
% 2. b

% the function will plot n Chebyshev polynomials
% on [-1, 1]

function x=cheb(n)
  t = linspace(-1, 1, 100)
  hold on
  % first two polynomials are given and will be used in
  % the recurrence formula
  t0 = @(x) ones(size(x))
  t1 = @(x) x
  
  plot(t, t0(t))
  plot(t, t1(t))
  
  % continue plotting polynomials up to degree n
  % obtained from the recurrence formula
  for i= 2:n
    a = t1
    t1 = @(x) 2.*x.*t1(x)-t0(x)
    plot(t, t1(t))
    t0 = a
  endfor
  title('First n Chebyshev polynomials')
endfunction
