
A = [3 1 1; -2 4 0; -1 2 -6];
b = [12; 2; -5];

x = A\b

eps = 1/ 10^5;
N=100;
w = 1.1;

printf("Jacobi\n")
j2 = jacobi(N, A, b, eps)
printf("Gauss-Seidel\n")
g2 = gauss(N, A, b, eps)
printf("Relaxation\n")
r2 = relaxation(N, A, b, eps, w)