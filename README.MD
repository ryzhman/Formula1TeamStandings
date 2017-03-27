https://travis-ci.org/ryzhman/Formula1TeamStandings.svg?branch=master

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



