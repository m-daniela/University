
function int = simp(f, a, b, n)
  # define the step
  h = (b - a)/ n;
  # split the interval
  xk = [a:h:b];
  # get the sum from k = 2; len of the middles of the intervals
  s1 = sum(f((xk(2:end) + xk(1:end-1))/2));
  # get the sum from k = 2; len - 1
  s2 = sum(f(xk(2:end-1)));
  int = (b-a)/(6*n)*(f(a) + f(b) + 4*s1 + 2 * s2);
end