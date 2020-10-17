% 10. For a list a1...an with integer and distinct numbers, define
% apredicate to determine all subsets with sum of elements divisible
% with n.
%
% subs(a1...an) = {
%    [], n = 0
%    a1 and subs(a2...an), otherwise}
%
% subs(A: list, R: list)
% subs(i, o)

subset([E], [E]).
subset([H|T], [H|R]):-subset(T, R).
subset([_|T], R):-subset(T, R).

%
% summ(a1...an, nr) = {
%     nr, n = 0
%     summ(a2...an, a1 + nr), otherwise}
% summ(A: list, NR: int, R: int)
% summ(i, i, o)
%
summ([], C, C).
summ([H|T], C, R) :-
    NC is C + H,
    summ(T, NC, R).

% is_div(a1...an, n) = {
%     true, summ(a1...an, nr) % n == 0
%     false, otherwise}
%
% is_div(A: list, N: int)
% is_div(i, i)

is_div(A, N) :-
    summ(A, 0, S),
    S mod N =:= 0.

% wrapper(l1...ln, nr) = {
%	is_div(subset(l1...ln), nr) }
% wrapper(L: list, NR: int, R: list)
% wrapper(i, i, o)

wrapper(L, N, R) :-
    subset(L, R),
    is_div(R, N).

% all(l1...ln, nr) = {reunion of wrapper(l1...ln, nr)}
% all(L: list, N: int, R: list)
% all(i, i, o)

all(L, N, R) :-
    findall(R1, wrapper(L, N, R1), R).
