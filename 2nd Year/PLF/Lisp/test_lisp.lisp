; Se da o lista neliniara formata din numere si atomi.
; Sa se determine lista elementelor numerice divizibile cu 5 din lista initiala, 
; de la orice nivel, in ordine inversa si o singura data.
; L= (5 a (b 10 (c 5 (12 (d 25) b 10)) 7)) => R = (10 25 5)

; delete_list(l1...ln) = {
; 	nil, l null
; 	l1 and delete_list(l2...ln), l1  number, l1 % 5 = 0
; 	delete_list(l1) and delete_list(l2...ln), l1 list
; 	delete_list(l2...ln), otherwise

; }

; delete_list(L: list, R: list)
; delete_list(i, o)

(defun delete_list(l)
	(cond
		((null l) nil)
		((and (numberp (car l)) (equal (mod (car l) 5) 0))
			 (cons (car l) (delete_list (cdr l))))
		((listp (car l)) (append (delete_list (car l)) (delete_list (cdr l))))
		(T(delete_list (cdr l)))
	)

)

; reverser(l1...ln) = {
; 	nil, l null
; 	reverse(l2...ln) and l1, otherwise
; }

; reverser(L: list, R: list)
; reverser(i, o)

(defun reverser(l)
	(cond
		((null l) nil)
		(T (append (reverser (cdr l)) (list (car l))))
	)
)

; remover(l1...ln, e) = {
; 	nil, l null
; 	remover(l2...ln, e), l1 = e
; 	l1 and remover(l2...ln, e), otherwise
; }
; remover(L: list, E: atom, R: list)
; (i, i, o)


(defun remover(l e)
	(cond
		((null l) nil)
		((equal (car l) e) (remover (cdr l) e))
		(T (cons (car l) (remover (cdr l) e)))
	)
)


; occ(l1...ln, e) = {
; 	0, l null
; 	1 + occ(l2...ln, e), l1 = e
; 	occ(l2...ln, e), otherwise
; }

; occ(L: list, E: atom, R: number)
; occ(i, i, o)

(defun occ(l e)
	(cond
		((null l) 0)
		((equal (car l) e) (+ 1 (occ(cdr l) e)))
		(T (occ(cdr l) e))
	)
)


; deleter(l1...ln, e){
; 	nil, l null
; 	deleter(l2...ln, e), occ(l1) > 1
; 	l1 and deleter(l2...ln, e), otherwise
; }

; deleter(L: list, E: number, R: list)
; deleter(i, i, o)

(defun deleter(l)
	(cond
		((null l) nil)
		((> (occ l (car l)) 1) (deleter (cdr l)))
		(T(cons (car l) (deleter (cdr l))))
	)
)

(defun wrapper2(l)
	(reverser (deleter (delete_list l)))
)

(print (wrapper2 '(5 a (b 10 (c 5 (12 (d 25) b 10)) 7))))

; (print (deleter (delete_list '(5 a (b 10 (c 5 (12 (d 25) b 10)) 7)))))