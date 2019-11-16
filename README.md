useful commands:

```
// runs tests and builds code
./gradlew clean build
```

```

./gradlew clean spotlessApply build
```

```
// builds docker image as well as code
./gradlew clean build buildImage
```

```
// runs application locally (on port 8081) using an in memory mongo database
./gradlew bootRun
```

```
// runs application locally within docker (exposed on port 8081) using a
// mongo real mongo instance in docker - note build and build image not
// required if already run previously
./gradlew clean build buildImage composeUp
```

```
// runs application locally within docker as above, but also executes any postman
// collections found within the spring app module against the running application
// to verify that the collections and the application is working as expected
./gradlew clean build buildImage composeUp postman
```

```
// stops and removes any running docker containers
./gradlew composeDown
```

TODO:

* Update travis CI pipeline to build docker image and run docker compose task and then run postman against the running service
* Change verification methods so there is one type per method but with a boolean flag
* Refactor to create config modules they are geography specific and can be used by spring app
* Change api module so it is not split into separate json and and api packages, the split is no longer needed / makes sense
* Add lockout policy maintenance apis
* Static api types so raml can be generated per geography api
* Verification method policies
* Deploy onto AWS
* Pipeline publishing libraries to maven automatically
* Client library