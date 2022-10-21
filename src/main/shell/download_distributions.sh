#!/bin/bash

unpack_and_delete_archives() {
  for f in *.zip; do unzip "$f"; done
  for f in *.tar.gz; do tar -xvzf "$f"; done

  rm *.zip *.tar
}

version="3.3.0"
path="downloads"
rm -rf $path
mkdir $path
cd $path

path="regular"
mkdir $path
cd $path

wget https://github.com/GMLC-TDC/HELICS/releases/download/v$version/Helics-$version-Linux-x86_64.tar.gz
wget https://github.com/GMLC-TDC/HELICS/releases/download/v$version/Helics-$version-macOS-universal2.zip
wget https://github.com/GMLC-TDC/HELICS/releases/download/v$version/Helics-$version-win32.zip
wget https://github.com/GMLC-TDC/HELICS/releases/download/v$version/Helics-$version-win64.zip

unpack_and_delete_archives

cd ..

path="shared"
mkdir $path
cd $path

wget https://github.com/GMLC-TDC/HELICS/releases/download/v$version/Helics-shared-$version-Linux-x86_64.tar.gz
wget https://github.com/GMLC-TDC/HELICS/releases/download/v$version/Helics-shared-$version-macOS-universal2.tar.gz
wget https://github.com/GMLC-TDC/HELICS/releases/download/v$version/Helics-shared-$version-win32.tar.gz
wget https://github.com/GMLC-TDC/HELICS/releases/download/v$version/Helics-shared-$version-win64.tar.gz

unpack_and_delete_archives

cd ..
