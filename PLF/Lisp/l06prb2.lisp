; 2. Return the list of nodes on the k-th level of a tree of type (1)

; (A 2 B 0 C 2 D 0 E 0)
;		A
;		/\
;		B C
;		  /\
;		  D E


; getChildren(l1...l2, k) = {
; 	l, k = 0
; 	getChildren(l2...ln, k - 1), otherwise
; }

; getChildren(L: list, K: number, R: list)
; getChildren(i, i, o)


(defun getChildren2(L K)
	(cond
		((equal K 0) L)
		(T (getChildren2 (cdr L) (- K 1)))
	)
)

; getKlvl(l1...ln, n, k) = {
; 	[l1], n = k
; 	nil, n != k, l2 = 0
; 	getKlvl(l3...ln, n + 1, k), n != k, l2 = 1
; 	[getKlvl(l3...ln, n + 1, k), getKlvl(getChildren (l3...ln, 1), n + 1, k), otherwise
; }

; getKlvl(L: list, N: number, K: number, R: list)
; getKlvl(i, i, i, o)

(defun getKlvl (L N K)
	(cond 
		((equal N K) (list (car L)))
		((and (equal (car (cdr L)) 0) (not (equal N K))) nil)
		((and (equal (car (cdr L)) 1) (not (equal N K))) (getKlvl (cdr (cdr L)) (+ N 1) K))
		(T (append (getKlvl (cdr (cdr L)) (+ N 1) K) (getKlvl (getChildren2 (cdr (cdr L)) 2) (+ N 1) K)))
	)
)

(print (getKlvl '(A 2 B 0 C 2 D 0 E 0) '0 '0)) ; (A)
(print (getKlvl '(A 2 B 0 C 2 D 0 E 0) '0 '1))	; (B C)
(print (getKlvl '(A 2 B 0 C 2 D 0 E 0) '0 '2))	; (D E)
(print (getKlvl '(A 2 B 0 C 2 D 0 E 0) '0 '3)) ; nil

