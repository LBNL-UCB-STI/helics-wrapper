[![](https://jitpack.io/v/LBNL-UCB-STI/helics-wrapper.svg)](https://jitpack.io/#LBNL-UCB-STI/helics-wrapper)

# helics-wrapper

### Build

```
./gradlew build
```

### Update library
1. Download distributions from release page, for example: https://github.com/GMLC-TDC/HELICS/releases/tag/v2.4.1. Not shared ones!
2. Go to `java` folder and get copy new versions of `helics.jar` to the `libs` folder of the project
3. Zip all libraries from `share` and `java` and put them to resources with related naming.  

### Building libhelicsJava.so for Ubuntu
- Pull helics builder docker image `docker pull helics/helics:helics-builder`
- Run that docker image with interactive bash `docker run -v C:\temp\helics:/helics -it helics/helics:helics3-builder /bin/bash` <- change mounting folder if needed
- `apt-get install openjdk-8-jdk -y`  
- `cd /helics && git clone https://github.com/GMLC-TDC/HELICS && cd HELICS && git checkout v2.6.1` <- change to your tag
- `mkdir build && cd build`
- Comment out the loading of library `System.loadLibrary` on this line https://github.com/GMLC-TDC/HELICS/blob/master/interfaces/java/addLoadLibraryCommand.cmake#L23, we load the library on our own here https://github.com/LBNL-UCB-STI/helics-wrapper/blob/master/src/main/java/com.github.beam/HelicsLoader.java#L26-L37 
- `cmake -DBUILD_JAVA_INTERACE=ON ..`
- `make -j8`
- Find and copy `libhelicsJava.so` and `helics-2.6.1.jar` to the linux archive (rename `helics-2.6.1.jar` to `helics.jar`)
