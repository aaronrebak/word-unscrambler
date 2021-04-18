# Word Unscramble API

This API manages unscrambling words.

## Coding Style

[IntelliJ](https://www.jetbrains.com/idea) is used as the default IDE.  
[Google Code Style](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml)
is used as the default Coding Style.

## Building

#### Artifact - Jar

~~~
$ ./gradlew clean api:build
~~~

#### Artifact - Container

[Jib](https://github.com/GoogleContainerTools/jib) is used for container builds.

~~~
Building to your local docker daemon
./gradle api:jibDockerBuild --image=<IMAGE_NAME>:<TAG>

Building without a docker daemon
./gradle api:jib --image=<IMAGE_NAME>:<TAG>
~~~  

## Running
By default, this is a console application.  
You can also toggle it into a web application by including the following in spring.profiles.include:  
* as-web


## Endpoints

Openapi is used to help describe and document this RESTful APIs.  
You can visit the Swagger-UI generated documentation (while the application is running) to see
information by suffixing the relevant host below with `/documentation/word-unscramble-api/ui`:

#### Local

~~~
http://localhost:8080
~~~  

## Core Testing Libraries

Use [JUnit5 Jupiter](https://junit.org/junit5/), [BDD AssertJ](https://assertj.github.io/doc/)
, [BDD Mockito](https://site.mockito.org/), in combination
with [Spring Test](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html)
where possible.  