function I = adaptive(f, a, b, err)
  I1 = simple_simp(f, a, b);
  I2 = simple_simp(f, a, (a + b) / 2) + simple_simp(f, (a + b) / 2, b);
  
  if abs(I1 - I2) < 15 * err
    I = I2;
  else
    I = adaptive(f, a, (a + b) / 2, err / 2) + adaptive(f, (a + b) / 2, b, err / 2);  
  endif
endfunction