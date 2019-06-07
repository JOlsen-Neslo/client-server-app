# client-server-app
An application containing a client and a server written in Java.

### Running the applications

First, run a maven build using the following command:

```
$ mvn install
```

Once that is complete, first run the server using the following command:

```
$ java -jar server/target/server-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

Once the server has started, you can then run a client with the following command:

```
$ java -jar server/target/server-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

You can then enter a number of equations and the result will be calculated for you!

#### Examples

```
$ 10+2*4
// expected result: 18

$ (1+1)*(4+4)
// expected result: 16


$ ((2+2) * 2) + 4
// expected result: 12

```