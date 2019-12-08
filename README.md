# Verification Context

[![Build Status](https://travis-ci.org/michaelruocco/verification-context.svg?branch=master)](https://travis-ci.org/michaelruocco/verification-context)
[![codecov](https://codecov.io/gh/michaelruocco/verification-context/branch/master/graph/badge.svg)](https://codecov.io/gh/michaelruocco/verification-context)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3cea44406f4141b487d8b13b19821734)](https://www.codacy.com/manual/michaelruocco/verification-context?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/verification-context&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/verification-context?branch=master)](https://bettercodehub.com/)

useful commands:

```gradle
// runs tests and builds code
./gradlew clean build
```

```gradle
// runs tests and builds code and applies rules to clean up code formatting etc
./gradlew clean spotlessApply build
```

```gradle
// builds docker image as well as code
./gradlew clean build buildImage
```

```gradle
// builds docker image as well as code and then pushes docker image
// NOTE - requires environment variables to be set for DOCKER_USERNAME and DOCKER_PASSWORD
// to be able to push to dockerhub
./gradlew clean build buildImage pushImage
```

```gradle
// runs application locally (on port 8081) using an in memory mongo database
./gradlew bootRun
```

```gradle
// runs application locally within docker (exposed on port 8081) using a
// mongo real mongo instance in docker - note build and build image not
// required if already run previously
./gradlew clean build buildImage composeUp
```

```gradle
// runs application locally within docker as above, but also executes any postman
// collections found within the spring app module against the running application
// to verify that the collections and the application is working as expected
./gradlew clean build buildImage composeUp postman
```

```gradle
// stops and removes any running docker containers
./gradlew composeDown
```

```gradle
// generates dependency graph diagrams in the build/reports/depdendency-graph folder
// one is idv specific and is useful for understanding the project architecture
// the other shows every project dependency and is a bit too busy to be useful, but is
// still interesting to see
./gradlew generateDependencyGraph
./gradlew generateDependencyGraphIdv
```

TODO:

*  Add "preCommit" task to gradle which will run build, spotless apply, build docker image and run postman tests after
docker compose tasks in order, which will give a simple command to build and test before adding a commit

*  Refactor to create config modules that are geography specific and can be used by spring app - partially complete

*  Change api module so it is not split into separate json and and api packages, the split is no longer needed / makes sense

*  Verification method policies

*  Client library

*  Pipeline publishing libraries to maven automatically from gradle / travis ci build pipeline

*  Deploy onto AWS (ideally would be automatic from travis ci pipeline)

*  Static api types so raml can be generated per geography api

*  Acceptance tests