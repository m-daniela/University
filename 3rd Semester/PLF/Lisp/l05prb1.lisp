; a)Write a function to return the n-th element of a list, or NIL if such an element does not exist.

; getN(l1...ln, n, pos) = {
;		nil, l = null
;		l1, n == pos
;		getN(l2...ln, n, pos + 1), otherwise
; }
; getN(L: list, N: int, Pos: int, R: int)
; getN(i, i, i, o)

(defun getN (l n pos)
	(cond
		((null l) nil)
		((= n pos) (car l))
		(T (getN (cdr l) n (+ pos 1)))
	)
)

; wrapperGetN(L: list, N: int, R: int)
; wrapperGetN(i, i, o)

(defun wrapperGetN (l n)
	(getN l n 0)
)

; (1 4 5 1 (a b)) 2 -> 5
; (3 4 5) 40 -> nil

; b)Write a function to check whether an atom E is a member of a list which is not necessarily linear.
; is_member(l1...ln, e) = {
; 		false, l == null
;		true, e == l1
;		is_member(l2...ln, e), l1 atom
;		is_member(l1, e), l1 list
; }
; is_member(L: list, E: int)
; is_member(i, i)

(defun is_member (l e)
	(cond
		((null l) nil)
		((and (atom (car l)) (equal (car l) e) T))
		((atom (car l)) (is_member (cdr l) e))
		((list (car l)) (is_member (car l) e) )
	)
)

; (3 5 b a (e)) e -> t
; 

; c) Write a function to determine the listof all sublists of a given list, on any level.
; to_list(l1...ln) = {
;		nil, l atom
;		l1 and to_list(l2...ln), otherwise
; }
; to_list(L: list, R: list)
; to_list(i, o)
; (1 2 (3 (4 5) (6 7)) 8 (9 10))
; (  (1 2 (3 (4 5) (6 7)) 8 (9 10))    (3 (4 5) (6 7))     (4 5)   (6 7)   (9 10) )

(defun to_list(l)
	(cond
		((atom l) nil)
		(T (apply 'append (list l) (mapcar 'to_list l)))


	)


)

; apply - parametri dinamici, se evalueaza, apoi se aplica functia
; (paramentri dinamici fiind a doua lista de la append)
; mapcar - aplica in mod repetat functia data 

; d)Write a function to transform a linear list into a set.


; occ(l1...ln, e) = {
;		0, l = null
;		1 + occ(l2...ln, e), l1 == e
;		occ(l2...ln), otherwise
;}
; occ(L:list, E: int, R: int)
; occ(i, i, o)


; to_set(l1...ln) = {
;		[], l = null
;		to_set(l2...ln), occ(l1) > 1
;		l1 and to_set(l2...ln), otherwise
;}
; to_set(L: list, R: list)
; to_set(i, o)

(defun occ(l e)
	(cond
		((null l) 0)
		((equal (car l) e) (+ (occ (cdr l) e) 1))
		(T (occ (cdr l) e))

	)

)

(defun to_set(l)

	(cond 
		((null l) nil)
		((> (occ l (car l)) 1) (to_set (cdr l)))
		(T (cons (car l) (to_set (cdr l))))

	)

)

; (1 2 1 4 1 2) -> (4 1 2)