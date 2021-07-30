format long;

A=[3, -1, 0, 0, 0, 0; -1, 3, -1, 0, 0, 0; 0, -1, 3, -1, 0, 0; 0, 0, -1, 3, -1, 0;
   0, 0, 0, -1, 3, -1; 0, 0, 0, 0, -1, 3];

b=[2; 1; 1; 1; 1; 2];

x = A\b

eps=10^(-3);
N=100;
w = 1.1;
printf("Jacobi\n")
j = jacobi(N, A, b, eps)
printf("Gauss-Seidel\n")
g = gauss(N, A, b, eps)
printf("Relaxation\n")
r = relaxation(N, A, b, eps, w)