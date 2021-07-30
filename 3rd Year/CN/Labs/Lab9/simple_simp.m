function res = simple_simp(f, a, b)
  res = (b - a) ./ 6 .* (f(a) + 4 .* f((a + b) / 2) + f(b));
endfunction