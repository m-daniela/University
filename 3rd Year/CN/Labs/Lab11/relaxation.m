function res = relaxation(N, A, b, eps, w)
  [m,n]=size(A);
  x=A\b;

  xJ_old=zeros(m,1);
  xJ_new=xJ_old;
  nr_it=0;
  while nr_it<=N

    for i=1:m
      aux = zeros(m, 1);
      aux(1:i) = xJ_new(1:i);
      aux(i+1:end) = xJ_old(i+1:end);
      aux_suma = A(i,:)*aux;
      xJ_new(i) = w/A(i,i)*(b(i) - aux_suma + A(i,i)*xJ_old(i)) + (1-w)*xJ_old(i); 
    end
    
    if abs(xJ_new-xJ_old)<eps
      fprintf('Solutia x este:\n')
      disp(xJ_new)
      res = xJ_new;
      fprintf('Numarul de iteratii:%d\n',nr_it)
      return
    end
   
    xJ_old=xJ_new;
    nr_it=nr_it+1;
  end
  disp('Numarul de iteratii a fost depasit')
 endfunction