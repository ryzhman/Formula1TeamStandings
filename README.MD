[![Build Status](https://travis-ci.org/ryzhman/Formula1TeamStandings.svg?branch=master)](https://travis-ci.org/ryzhman/Formula1TeamStandings)

## Run application ##
To run a REST service you should start Redis server locally. You can download it from official Redis webpage ([Linux](https://redis.io/download) | [Windows](https://github.com/MSOpenTech/redis/releases))

## Samples of requests ##
### To add new data for a particular constructor or create a new one you should create a POST request with next configurations ### 
URL
```
localhost:8080/constructors/constructor/MACLAREN
``` 
Request Body
```{
"title": "MacLaren",
"points": 103
} 
```

### To get current constructors standings create a GET request with next configurations ###
URL
```
localhost:8080/constructors/standings
```

## To add multiple constructors you can create a POST method ###
URL
```
localhost:8080/constructors/constructor

```
Request Body
```
[{
"title": "WILLIAMS",
"points": 93
},
{
"title": "MacLaren",
"points": 103
},
{
"title": "Ferrari",
"points": 102
}]
```

### To add new data for a particular driver or create a new one you should create a POST request with next configurations ### 
URL
```
localhost:8080/drivers/driver/Sebastian Vettel
```
Request Body

```
{
	"nationality": "Germany",
	"teamTitle": "Ferrari",
	"points": 123,
	"wins": 10
}
```

### To add multiple drivers you can create a POST method ###
URL
```
localhost:8080/drivers/driver
```
Request Body
```
[{
	"name": "Sebastian Vettel",
	"nationality": "Germany",
	"teamTitle": "Ferrari",
	"points": 123,
	"wins": 10
},
{
	"name": "Lewis Hamilton",
	"nationality": "UK",
	"teamTitle": "Mercedes",
	"points": 122,
	"wins": 8
}]
```

## Redis data structure ##
All the data is persisted in Redis DB in simple strings
Example of Redis keys:
```
"STANDINGS:CONSTRUCTORS:Ferrari"

get "STANDINGS:DRIVERS:Sebastian Vettel"
"{\"id\":null,\"name\":\"Sebastian Vettel\",\"nationality\":\"n/a\",\"wins\":10,\"points\":123,\"teamTitle\":\"Ferrari\"
}"
```



