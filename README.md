# Catalog electronic

## Grad dificultate 
* mediu
## Timp de implementare 
* 25-30 ore

## Implementare
 Implementarea catalogului electronic a constat, in primul rand, din implementarea
claselor si a metodelor din cerinta, la care am adaugat:

* Un ArrayList cu elemente de tip CourseBuilder in clasa Catalog

## Testare
Pentru testarea aplicatiei, am creat fisierele:
* studenti.in - contine numele a 100 de studenti + partialScore si ExamScore
* parents.in – contine numele a 200 de parinti
* asistenti.in – contine numele a 62 de asistenti
* cursuri.in – contine numele a 7 cursuri, in format: nume_curs, numele
profesorului_titular_fiecarui_curs, numarul_de_credite

Testarea aplicatiei fara interfata grafica(cerinta 1.10) am realizat-o prin formarea a 4
grupe de studenti, cate 25 in fiecare grupa, formarea a 7 cursuri (6 de tip FullCourse si 1 de tip
PartialCourse), adaugarea asistentilor, setarea numelor parintilor fiecarui student.
In clasa ScoreVisitor profesorii si asistentii trec notele fiecarui student conform
fisierului studenti.in, iar notificarile transmise parintilor pot fi observate in fisierul
notifications.out, in care sunt 1400 de notificari: 100 parinti, pentru a simplifica putin, am redus
numarul parintilor care primsec notificari * 7 cursuri * de doua ori(o data pentru asistent, o data
pentru profesor).
Testarea aplicatiei cu interfata grafica se face creand conturi pentru fiecare tip de
User(Student, Teacher, Parent, Assistant) pe pagina ce rezulta apasand butonul SIGN UP, conditia
fiind ca un user de acelasi tip si acelasi nume sa nu fi fost creat deja. Studentii creati cu interfata
grafica sunt adaugati intr-o grupa separata fata de cei adaugati fara interfata grafica, pentru a se
face distinctie intre ei la apasarea butonului VALIDEAZA NOTA de pe pagina corespunzatoare
Teacherului sau Assistantului, si implicit la apelarea metodelor din clasa ScoreVisitor. Se vor crea
6 fisiere:
* bonusParoleStudenti.txt, in format: prenume_student,nume_student:parola_hash
* bonusParoleProfesori.txt, in format: prenume_profesor,nume_profesor:parola_hash

* bonusParoleAsistenti.txt, in format:prenume_asistent,nume_asistent:parola_hash
* bonusParoleParinti.txt, in format:
prenume_parinte,nume_parinte,prenume_student,nume_student:parola_hash. Am
considerat necesara adaugarea numelui studentului in fisierul in care sunt salvati parintii
pentru a putea crea notificarile corespunzatoare si trimise parintelui corect.
* bonusNoteExamen.txt si bonusNotePartiale.txt in format:
prenume_student,nume_student,nota_curs,nume_curs, aceste fisiere sunt modificate
in momentul in care se apeleaza metoda visit din ScoreVisitor
