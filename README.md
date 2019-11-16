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
// stops and removes any running docker containers
./gradlew composeDown
```

TODO:

* Change channel API to use API type provided by geography / fix issuerSessionId rendering as null in response when not provided
* Add gradle postman plugin so that postman collections / tests can be run from build gradle build and build pipeline
* Change verification methods so there is one type per method but with a boolean flag
* Refactor to create config modules they are geography specific and can be used by spring app
* Change api module so it is not split into separate json and and api packages, the split is no longer needed / makes sense
* Add lockout policy maintenance apis
* Static api types so raml can be generated per geography api
* Verification method policies
* Deploy onto AWS
* Pipeline publishing libraries to maven automatically
* Client library