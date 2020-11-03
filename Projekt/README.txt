docker run --name projekt_database_1  -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7

1/docker-compose pro vytvoření DB


2/Dockerfile pro vytvoření kontejneru pro Aplikaci a naimportování dat do DB

//po vytvoření db a importu dat
//do konzole
docker build -f Dockerfile -t ppro_horak .

docker run -p 8085:9000 ppro_horak