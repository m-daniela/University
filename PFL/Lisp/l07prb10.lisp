; 10.Define  a  function  that  replaces  one  node  with  another  one  
; in  a  n-tree represented  as:  root list_of_nodes_subtree1... 
; list_of_nodes_subtreen) Eg: tree is (a (b (c)) (d) (e (f))) and node 'b'
; will be replace with node 'g' => tree (a (g (c)) (d) (e (f)))}


; replace2(l, a, b) = {
; 	b, l atom, a = l
; 	l, l atom
; 	replace2
; }

; replace2(L: list, A: number, B: number, R: list)
; replace(i, i, i, o)


(defun replace2 (L A B)
	(cond	
		((and (atom L) (equal A L)) B)
		((atom L) L)
		(T (mapcar #'(lambda (X) (replace2 X A B)) L))
	)
)

(print (replace2 '(a (b (c)) (d) (e (f))) 'b 'g))

