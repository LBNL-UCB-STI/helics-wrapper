[![](https://jitpack.io/v/LBNL-UCB-STI/helics-wrapper.svg)](https://jitpack.io/#LBNL-UCB-STI/helics-wrapper)

# helics-wrapper

### Build

```
./gradlew build
```

### Update library
1. Download distributions from release page with a shell script - `src/main/shell/download_distributions.sh`
2. Copy `helics.jar` to the `libs` folder of the project. The jar file might be copied from macOS, win32, win64 distributions (from java folder) or might be built with docker image together with `libhelicsJava.so`.
3. Zip all libraries from `share` and `java` and put them to resources with related naming with a shell script - `src/main/shell/pack_archives.sh`

### Building libhelicsJava.so for Ubuntu
- Pull helics builder docker image `docker pull helics/helics:helics-builder`
- Run that docker image with interactive bash `docker run -v C:\temp\helics:/helics -it helics/helics:helics-builder /bin/bash` <- change mounting folder if needed
- `apt-get update && apt-get install openjdk-8-jdk vim -y`  
- `cd /helics && git clone https://github.com/GMLC-TDC/HELICS && cd HELICS && git checkout v3.3.0 && mkdir build && cd build` <- change to your tag
- `vim ../interfaces/java/addLoadLibraryCommand.cmake` Comment out the loading of library `System.loadLibrary` on lines https://github.com/GMLC-TDC/HELICS/blob/main/interfaces/java/addLoadLibraryCommand.cmake#L21-L26, we load the library on our own here https://github.com/LBNL-UCB-STI/helics-wrapper/blob/master/src/main/java/com.github.beam/HelicsLoader.java#L26-L37
- `cmake -DHELICS_BUILD_JAVA_INTERFACE=ON .. && make -j8`
- Copy `libhelicsJava.so` (lib/libhelicsJava.so)  and `helics.jar` (interfaces/java/jarproject/buildjar/helics.jar) to the linux archive
