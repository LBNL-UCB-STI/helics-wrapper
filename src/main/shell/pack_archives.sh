#!/bin/bash


path="archives"
rm -rf $path && mkdir $path && cd $path


os="unix" && rm -rf $os && mkdir $os
# libhelicsJava.so should be built beforehand
# in readme there is an instruction how to do so with helics-builder docker image
cp ../libhelicsJava.so $os/libhelicsJava.so
cp ../downloads/shared/*Linux*/lib64/libhelics.so $os/libhelicsSharedLib.so
cp ../downloads/regular/*Linux*/lib64/libzmq.so $os/libzmq.so
cd $os && zip ../$os.zip * && cd ..
mv $os.zip ../../resources/helics-binaries/$os.zip


os="mac" && rm -rf $os && mkdir $os
cp ../downloads/regular/*macOS*/java/libhelicsJava.dylib $os/libhelicsJava.dylib
cp ../downloads/shared/*macOS*/lib/libhelics.dylib $os/libhelicsSharedLib.dylib
cp ../downloads/regular/*macOS*/lib/libzmq.dylib $os/libzmq.dylib
cd $os && zip ../$os.zip * && cd ..
cp $os.zip ../../resources/helics-binaries/$os.zip
# it is called 'universal' in downloads, so, probably, the archive should be the same
mv $os.zip ../../resources/helics-binaries/$os-arm64.zip


os="win32" && rm -rf $os && mkdir $os
cp ../downloads/regular/*$os*/java/helicsJava.dll $os/helicsJava.dll
cp ../downloads/regular/*$os*/java/helicsJava.lib $os/helicsJava.lib
cp ../downloads/shared/*$os*/lib/helics.lib $os/helicsSharedLib.dll
cd $os && zip ../$os.zip * && cd ..
mv $os.zip ../../resources/helics-binaries/$os.zip


os="win64" && rm -rf $os && mkdir $os
cp ../downloads/regular/*$os*/java/helicsJava.dll $os/helicsJava.dll
cp ../downloads/regular/*$os*/java/helicsJava.lib $os/helicsJava.lib
cp ../downloads/shared/*$os*/lib/helics.lib $os/helicsSharedLib.dll
cd $os && zip ../$os.zip * && cd ..
mv $os.zip ../../resources/helics-binaries/$os.zip

