**For the sake of time, the system currently works as follows:**

Considering all the requirements, I used microservices architecture for this task.
I implemented Kafka for asynchronous calls to fulfill the requirement "3rd party exchange rates change every 1 minute". In addition, I used a cache for currency pairs. When a pair is updated via Kafka, it is removed from the cache. Instead of using a per-service cache, a distributed cache like Redis or Hazelcast should be used over here.


I implemented a pessimistic lock for the Account-to-Account API.

Run the following command to run the program:

``` bash
docker-compose up
```

**Upgrades:**

I have currently used JUnit tests for the service layer. Integration tests should also be written for controller and service layers.

I would add a gateway to the system and create multiple instances of it (geographically, etc.) to reduce the load on the application.
Additionally, JWT token authentication should be added to the `ms-user` module**
