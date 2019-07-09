# rest-producer

rest-producer

You can customize the database (H2 by default and for @test) and the initial data in this files:

scr/main/resources/city_list.txt

```
CityId,CityName
"AMS","Amsterdam"
"ATH","Athens"
...
```

scr/main/resources/city_distance_list.txt

```
Id,CityOriginId,CityDestinationId,distance(km)
"AMS1","AMS","LON",494
"AMS2","AMS","BER",649
...
```

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
http://localhost:8098/city/itinerary-short?cityOriginId=MAD&cityDestinationId=BER&departureTime=2019-07-10T01:30:00.000Z
http://localhost:8098/city/itinerary-less?cityOriginId=MAD&cityDestinationId=BER&departureTime=2019-07-10T01:30:00.000Z
http://localhost:8098/city-distance?cityOriginId=MAD

user: user_rest
password: paSs123_w0rd

5) Swagger Rest doc

http://localhost:8098/swagger-ui.html

See [eureka-dijkstra](https://github.com/albertoolivan/eureka-dijkstra) for more info