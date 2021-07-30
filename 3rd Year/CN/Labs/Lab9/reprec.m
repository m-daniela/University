function res = reprec(f, a, b, n)
  # compute the xi and add f(xi) to the sum
  x1 = a + (b - a) / (2 * n);
  s = f(x1);
  for i = 2:n
    xi = x1 + (i - 1) * (b - a) / n;
    s = s + f(xi);
  endfor
  res = (b - a) / n * s;
endfunction