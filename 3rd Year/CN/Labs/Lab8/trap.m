
function int = trap(f, a, b, n)
  # define the step
  h = (b - a)/ n;
  # split the interval
  xk = [a:h:b];
  # get the sum from k = 2; len - 1
  s = sum(f(xk(2:end-1)));
  int = (b-a)/(2*n)*(f(a) + f(b) + 2 * s);
end