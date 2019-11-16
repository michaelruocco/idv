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

1. Extract channels / lockout policies so they can be provided by geography
2. Change verification methods so there is one type per method but with a boolean flag
3. Refactor to create config modules they are geography specific and can be used by spring app
4. Add lockout policy maintenance apis
5. Static api types so raml can be generated per geography api
6. Verification method policies
7. Deploy onto AWS
8. Pipeline publishing libraries to maven automatically
9. Client library