# rest-producer

0) Pre requirement - install rest.dto-1.0.0-SNAPSHOT.jar 

https://github.com/albertoolivan/ch.rest.dto.git
$ mvn install

1) Maven install to get rest-producer-1.0.0-SNAPSHOT.jar

$ mvn install

2) Create a docker image rest-producer

$ docker build -t rest-producer .

3) Execute docker container

$ docker run -p 8098:8098 rest-producer

4) Test in browser (need service-registry up)

http://localhost:8098/city/info/MAD
http://localhost:8098/city/all
http://localhost:8098/city/itinerary-short?cityOriginId=MAD&cityDestinationId=BER
http://localhost:8098/city/itinerary-less?cityOriginId=MAD&cityDestinationId=BER
http://localhost:8098/city-distance?cityOriginId=MAD

user: user_rest
password: paSs123_w0rd

5) Swagger Rest doc

http://localhost:8098/swagger-ui.html