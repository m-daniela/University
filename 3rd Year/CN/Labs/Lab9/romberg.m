function res = romberg(f, qtprev, k, a, b, eps)
  h = b - a;
  
  # get the sum according to the formula
  j = 1:1:2^(k-1);
  # a missing paranthesis here
  s = sum(f(a + (2 .* j - 1)/(2^k) * h));

  # get the value of the kth term
  qt = 1 / 2 * qtprev + h / (2^k) * s;

  if abs(qt - qtprev) <= eps
    k
    res = qt;
  else
    res = romberg(f, qt, k + 1, a, b, eps);
  end
  
end
