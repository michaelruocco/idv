# IDV

[![Build Status](https://travis-ci.org/michaelruocco/idv.svg?branch=master)](https://travis-ci.org/michaelruocco/idv)
[![codecov](https://codecov.io/gh/michaelruocco/idv/branch/master/graph/badge.svg)](https://codecov.io/gh/michaelruocco/idv)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b07013490db7485fb7fc4b3ea542ed9d)](https://www.codacy.com/manual/michaelruocco/idv?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/idv&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/idv?branch=master)](https://bettercodehub.com/)

## TODO list

*  Use identity data to determine method eligibility

*  Add dynamo db persistence of identity data (phone numbers / emails / devices / accounts)

*  Create physical PINsentry device loader and populate pinsentry devices onto identity

*  Create email loader and populate emails onto identity

*  Think about turning provided alias into provided aliases

*  Clean up api modules

*  Add delete endpoints for verification and lockout policies

## Future Enhancements / Ideas

*  Put object mothers in separate module or move into production code packages

*  All eligible flag against delivery method (for otp) and card (for pinsentry)

*  Add folders to postman collection and try using a single one again

*  Add properly formatted error handling for OTP scenarios

*  Load phone numbers from database so we don't have use stubbed environment variables to change phone numbers

*  Try removing NAT gateway from AWS templates as they are costly when you leave the ECS cluster running

*  Implement token validation for access control and see if it is possible to format responses based on user profile

*  Verification method policies

*  Verification method policies updatable on the fly

*  Once policies are in database look at options for cache layer to reduce number of db hits, first simple idea is to
   add a caching decorator to the policy dao classes where we can pass a configurable duration of how long to cache for,
   on first read load all policies into a map if any writes are made then update map. Would need to think about
   messaging between multiple instances, probably best option for this would be a pub sub notification that would use
   something like sns or kafka then each instance could listen for updates and refresh cache as needed.

*  Deploy onto AWS (ideally would be automatic from travis ci pipeline)

*  Acceptance tests (cucumber / karate) alongside postman tests already in place

*  Add "preCommit" task to gradle which will run build, spotless apply, build docker image and run postman tests after
docker compose tasks in order, which will give a simple command to build and test before adding a commit

## Useful commands

```gradle
// runs tests and builds code
./gradlew clean build
```

```gradle
// runs tests and builds code and applies rules to clean up code formatting etc
./gradlew clean spotlessApply build
```

```gradle
// runs tests and builds code and applies rules to clean up code formatting etc
// also runs integration tests (these are separated out as they are slower tests to run)
./gradlew clean spotlessApply build integrationTest
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

// does the same as above but also overrides the phone number used in the stubbed data returned
./gradlew bootRun -Dstubbed.phone.number=+4407808123456
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
// perform all of the above tasks, useful before committing to ensure
// that all tests are passing and code has had spotless rules applied to clean
// up code.
./gradlew clean spotlessCheck build integrationTest codeCoverageReport buildImage composeUp postman composeDown
```

```gradle
// check that dependencies are up to date
./gradlew dependencyUpdates
```

```gradle
// generates dependency graph diagrams in the build/reports/depdendency-graph folder
// one is idv specific and is useful for understanding the project architecture
// the other shows every project dependency and is a bit too busy to be useful, but is
// still interesting to see
./gradlew generateDependencyGraph
./gradlew generateDependencyGraphIdv
```


```aws
aws cloudformation create-stack --stack-name idv-dev-ecs --template-url https://idv-cloud-formation-templates.s3-eu-west-1.amazonaws.com/cluster-fargate-private-vpc.yml --parameters ParameterKey=EnvironmentName,ParameterValue=idv-dev --capabilities CAPABILITY_IAM --region eu-west-1

aws cloudformation create-stack --stack-name idv-dev-alb --template-url https://idv-cloud-formation-templates.s3-eu-west-1.amazonaws.com/alb-external.yml --parameters ParameterKey=EnvironmentName,ParameterValue=idv-dev --region eu-west-1

aws cloudformation create-stack --stack-name idv-dev-verification-context --template-url https://idv-cloud-formation-templates.s3-eu-west-1.amazonaws.com/service-fargate-private-subnet-public-lb.yml --parameters ParameterKey=EnvironmentName,ParameterValue=idv-dev ParameterKey=ServiceName,ParameterValue=verification-context ParameterKey=ImageUrl,ParameterValue=michaelruocco/verification-context-spring-app:latest ParameterKey=DesiredCount,ParameterValue=1 ParameterKey=Role,ParameterValue=ecsTaskExecutionRole --region eu-west-1
```