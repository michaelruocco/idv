useful commands:

```
// runs tests and builds code
./gradlew clean build
```

```
// runs tests and builds code and applies rules to clean up code formatting etc
./gradlew clean spotlessApply build
```

```
// builds docker image as well as code
./gradlew clean build buildImage
```

```
// builds docker image as well as code and then pushes docker image
// NOTE - requires environment variables to be set for DOCKER_USERNAME and DOCKER_PASSWORD
// to be able to push to dockerhub
./gradlew clean build buildImage pushImage
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

* Change verification methods so there is one type per method but with a boolean flag
* Refactor to create config modules they are geography specific and can be used by spring app
* Change api module so it is not split into separate json and and api packages, the split is no longer needed / makes sense
* Add lockout policy maintenance apis
* Verification method policies
* Client library
* Pipeline publishing libraries to maven automatically from gradle / travis ci build pipeline
* Deploy onto AWS (ideally would be automatic from travis ci pipeline)
* Static api types so raml can be generated per geography api