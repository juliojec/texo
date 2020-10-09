## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

# texo http requests

Take the intervals  
http://localhost:8080/movie/interval

List of films
http://localhost:8080/movie/list

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `texo-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it?s not an _?ber-jar_ as the dependencies are copied into the `target/lib` directory.
If you want to build an _?ber-jar_, just add the `--uber-jar` option to the command line:
```shell script
./mvnw package -PuberJar
```

The application is now runnable using `java -jar target/texo-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/texo-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.
