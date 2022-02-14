# Test task

### Description
Creation REST API for persisting washing machine states.
There are such entities as a model,  a machine and a program.
Implemented with their helps a simple logic for persisted a washing machine state

### REST API Documentation
Documentation was generated with spring rest docs. You can check out the link
[here](./docs/index.html). 

### Building & deployment
* `mvn clean install`
* `mvn jib:build`
* `docker-compose up -d`

If everything's ok you should see the documentation reference in your browser http://localhost:8080/docs/index.html


