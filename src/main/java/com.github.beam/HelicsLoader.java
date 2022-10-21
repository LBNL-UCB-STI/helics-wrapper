package com.github.beam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HelicsLoader {
    private static final String mac = "mac.zip";
    private static final String mac_arm64 = "mac-arm64.zip";
    private static final String unix = "unix.zip";
    private static final String windows32 = "win32.zip";
    private static final String windows64 = "win64.zip";

    private static void loadLibrary(File basePath, String libraryName) {
        String libraryPath = Paths.get(basePath.getAbsolutePath(), libraryName).toString();
        try {
            System.load(libraryPath);
            System.out.println("Loaded " + libraryPath);
        } catch (Exception ex) {
            System.out.println("Was not able to load " + libraryPath);
            throw new RuntimeException(ex);
        }
    }

    public static void load() {
        File tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "helics").toFile();

        tempDir.mkdirs();
        tempDir.deleteOnExit();

        String libraryArchive = "helics-binaries/" + getLibraryArchive();

        unzipLibArchive(tempDir, libraryArchive);

        String libname = System.mapLibraryName("helicsJava");

        // this is required because for windows and linux we need to manually
        // load all dependencies before loading actual library
        if (libraryArchive.contains("win")) {
            loadLibrary(tempDir, "helicsSharedLib.dll");
        } else if (libraryArchive.contains(unix)) {
            loadLibrary(tempDir, "libzmq.so");
            loadLibrary(tempDir, "libhelicsSharedLib.so");
        }
        loadLibrary(tempDir, libname);
    }

    private static void unzipLibArchive(File directory, String libraryArchive) {
        try (ZipInputStream zis = new ZipInputStream(HelicsLoader.class.getResourceAsStream("/" + libraryArchive))) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String fileName = Paths.get(directory.getAbsolutePath(), entry.getName()).toString();

                new File(fileName).delete();

                try (FileOutputStream fout = new FileOutputStream(fileName)) {
                    byte[] buffer = new byte[1024];
                    int bufferSize = zis.read(buffer);
                    while (bufferSize != -1) {
                        fout.write(buffer, 0, bufferSize);
                        bufferSize = zis.read(buffer);
                    }
                }

                zis.closeEntry();
                entry = zis.getNextEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getLibraryArchive() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            boolean is64bit;
            if (os.contains("windows"))
                is64bit = System.getenv("ProgramFiles(x86)") != null;
            else
                is64bit = System.getProperty("os.arch").contains("64");

            if (is64bit)
                return windows64;
            else
                return windows32;
        }

        if (os.contains("mac"))
            if (System.getProperty("os.arch").contains("aarch64"))
                return mac_arm64;
            else
                return mac;

        if (os.contains("nix") || os.contains("nux"))
            return unix;

        throw new RuntimeException(String.format("Os %s is not supported by helics-wrapper", os));
    }
}
