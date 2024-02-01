**For the sake of time, the system currently works as follows:**

Considering all the requirements, I used microservices architecture for this task.
I implemented Kafka for asynchronous calls to fulfill the requirement "3rd party exchange rates change every 1 minute". In addition, I used a cache for currency pairs. When a pair is updated via Kafka, it is removed from the cache.

I implemented a pessimistic lock for the Account-to-Account API.

Run the following command to run the program:

``` bash
docker-compose up
```

**Upgrades:**

I have currently used JUnit tests for the service layer. It is important to extend the test coverage to include integration tests and controllers should also be tested.

I suggest adding a gateway to the system and creating multiple instances of it to efficiently distribute the load across the application.

Additionally, the `ms-user` module should be upgraded to implement JWT token authentication.**