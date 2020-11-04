docker run --name projekt_database_1  -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7

1/docker-compose pro vytvoření DB


2/Dockerfile pro vytvoření kontejneru pro Aplikaci a naimportování dat do DB

//po vytvoření db a importu dat
//do konzole
docker build -f Dockerfile -t ppro_horak .

docker run -p 8085:9000 ppro_horak


//////////////////////
doplněno po odeslání projektu :
Zapomněl jsem, říct loginy a hesla.
První sloupec je login účtu.
Hesla jsou stejná jako login číslo 1 znamená admin, 2 pracovník, 3 zákazník.
Pracovník je všemocný jako admin, ale nemůže měnit údaje u účtu(Heslo a typ účtu).
Nejvíc ukázek je u palivo a u cibulky.


ANONYM,3
palivo,1
stalin,3
gotwald,3
jansky,3
bezruc,3
kritek,3
radecky,2
braun,3
cibulka,3
