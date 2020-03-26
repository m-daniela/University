% a. For a list of integer number,write a predicate 
% to add in list after 1-st, 3-rd, 7-th, 15-th element a given value e.

% add(L: list, E: integer, P: integer, R: list)
% add(i, i, i, o)
% add(l1...ln, e, p) = {
%	[], n = 0,
%	l1 + e + add(l2...ln, e, p + 1), p = 3 or p = 5 or p = 7 or p = 15,
%	l1 + add(l2...ln, e, p + 1), otherwise}
 
add([], _, _, []).
add([H|T], E, P, [H, E|R]):- P =:= 1, !,
    NP is P + 1,
    add([H|T], E, NP, R).
add([H|T], E, P, [H, E|R]):- P =:= 3, !,
    NP is P + 1,
    add([H|T], E, NP, R).
add([H|T], E, P, [H, E|R]):- P =:= 7, !,
    NP is P + 1,
    add([H|T], E, NP, R).
add([H|T], E, P, [H, E|R]):- P =:= 15, !,
    NP is P + 1,
    add([H|T], E, NP, R).
add([H|T], E, P, [H|R]):- 
    NP is P + 1,
    add(T, E, NP, R).

% insert(L: list, E: integer, R: list)
% insert(i, i, o)
% insert(l1...ln, e) = {
%	add(l1...ln, )
insert(L, E, R) :- add(L, E, 1, R).

% b. For a heterogeneous list, formed from integer numbers and list 
% of numbers; add in every sublist after 1-st, 3-rd, 7-th, 15-th element 
% the value found before the sublist in the heterogenous list. The list 
% has the particularity that starts with a number and there aren’t two 
% consecutive elements lists.
% Eg.: [1, [2, 3], 7, [4, 1, 4], 3, 6, [7, 5, 1, 3, 9, 8, 2, 7], 5] =>
% [1, [2, 1, 3], 7, [4, 7, 1, 4, 7], 3, 6, [7, 6, 5, 1, 6, 3, 9, 8, 2, 6, 7], 5]

% het(L: list, R: list)
% het(i, o)
% het(l1...ln) = {
%	l1 + insert(l2, l1) + het(l2...ln), l2 is a list and l1 a number
%	(l2...ln), otherwise}

het([], []).
het([E], [E]).
het([H1, H2|T], [H1,HR|R]) :- is_list(H2), number(H1), !,
    insert(H2, H1, HR),
    het(T, R).
het([H|T], [H|R]) :-
    het(T, R).
