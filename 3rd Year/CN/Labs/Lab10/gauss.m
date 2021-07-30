
function res = gauss(n, A, b)
  x = zeros(n, 1);
  for k = 1:n-1 
    # interchange the lines if the max 
    # is not on the main diagonal
    for j = k+1:n
      if abs (A(k, k)) < abs(A(j, k))
        A([k j], :) = A([j k], :);
        b([k j]) = b([j k]); 
      endif
    endfor

    # make zeros under the main diagonal
    # apply line transformations for the 
    # free term vector
    for i = k+1:n
      a = A(i, k) / A(k, k);
      A(i, :) = A(i, :) - a * A(k, :);
      b(i) = b(i) - a * b(k);
    endfor
  endfor 
  
  # compute the solution by solving 
  # the system backwards
  for i = n:-1:1
    s = b(i);
    for j = i+1:n
      s = s - A(i, j) * x(j);
    end
    x(i) = s/A(i, i);
  end
  
  res = x;
end






