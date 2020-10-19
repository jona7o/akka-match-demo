# akka-math-demo

This is a small akka-http demo project for Exercise 4 of Microservices Lecture at TH Rosenheim. It has a RPC like HTTP Endpoint which can do some math operations.

- run `docker-compose up -d`
- Try to reach http://127.0.0.1:8080/math/isPrime/31 
- Try to reach http://127.0.0.1:8081/math/power/23
- Develop an "api gateway" which calls both services parallel and combine the responses if they arrive under 4s.
- run `docker-compose down`

[Tobias Jonas - Microservices - TH Rosenheim](https://innFactory.de)