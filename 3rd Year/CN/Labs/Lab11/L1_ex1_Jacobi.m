A=[3, -1, 0, 0, 0, 0; -1, 3, -1, 0, 0, 0; 0, -1, 3, -1, 0, 0; 0, 0, -1, 3, -1, 0;
   0, 0, 0, -1, 3, -1; 0, 0, 0, 0, -1, 3];

b=[2; 1; 1; 1; 1; 2];

eps=10^(-3);
N=100;
w = 1.1;

j = jacobi(N, A, b, eps);
g = gauss(N, A, b, eps)
r = relaxation(N, A, b, eps, w);

##[m,n]=size(A);
##x=A\b
##
##xJ_old=zeros(m,1);
##xJ_new=xJ_old;
##nr_it=0;
##while nr_it<=N
##  
##  for i=1:m
##    aux_suma = A(i,:)*xJ_old;
##    xJ_new(i) = 1/A(i,i)*(b(i)-aux_suma+A(i,i)*xJ_old(i)); 
##  end
##  
##  if abs(xJ_new-xJ_old)<eps
##    fprintf('Solutia x este:\n')
##    disp(xJ_new)
##    fprintf('Numarul de iteratii:%d\n',nr_it)
##    return
##  end
## 
##  xJ_old=xJ_new;
##  nr_it=nr_it+1;
##end
##disp('Numarul de iteratii a fost depasit')
