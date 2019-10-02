ECDSA
-------------------------------------------------------------------------------

This project is a basic implementation of the Elliptic Curve Digital Signature Algorithm(ECDSA) which uses NIST-recommended curves over the prime field *GF(p)*. Main features of the program are: *Key generation*, *file signature generation* and *verification*. **This software has been created as a training exercise and is probably unsafe. Never use it as a substitute for real encryption software!**
### Supported curves:
* *P-192*
* *P-224*
* *P-256*
* *P-384*
* *P-521*

## Prerequisites
* [Java 11](https://jdk.java.net/11/)
* [JavaFX 11](https://openjfx.io/)

## Building and running
### To build from source - run in the command line:
On ***nix**:
```
./gradlew build
```

On **Windows**:

```
gradlew.bat build
```

### To run:
```
gradlew run
```

OR

```
gradlew jar
```

and

```
java -jar --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.fxml ECDSA.jar
```

Where `$PATH_TO_FX` is the location of the JavaFX runtime libraries.

## License
The source code is licensed and distributed under the MIT License, for more information see the LICENSE file.
