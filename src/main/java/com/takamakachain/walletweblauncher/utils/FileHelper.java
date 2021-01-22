/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.takamakachain.walletweblauncher.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author giovanni.antino
 */
public class FileHelper {

    public static Path getDefaultApplicationDirectoryPath() throws Exception {
        String userHome = System.getProperty("user.home");
        //Log.log(Level.INFO, userHome);
        return Paths.get(userHome, ".tkm-chain");
    }

    public static void createDir(Path directoryPathAndName) throws IOException {
        Files.createDirectory(directoryPathAndName);
    }

    public static void initProjectFiles() throws Exception {
        if (!homeDirExists()) {
            createDir(getDefaultApplicationDirectoryPath());
            //Log.log(Level.INFO , "Home directory created");
        }
    }

    public static boolean homeDirExists() throws Exception {
        Path applicationDirectoryPath = getDefaultApplicationDirectoryPath();
        File applicationDirectoryFilePointer = applicationDirectoryPath.toFile();
        return applicationDirectoryFilePointer.isDirectory();
    }

    public static void deleteFolderContent(File f) {
        if (f.isFile()) {
            //F.b("not a folder, do nothing " + f.toString());
        }

        if (f.isDirectory()) {
            for (File listFile : f.listFiles()) {
                try {
                    delete(listFile);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean directoryExists(Path filePointer) {
        return filePointer.toFile().isDirectory();
    }

    public static boolean fileExists(Path file) {
        return file.toFile().isFile();
    }

    public static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                delete(c);
            }
        }
        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + f);
        }
    }

    public static void delete(Path file) throws IOException {
        File f = new File(file.toString());
        if (f.isDirectory()) {
            throw new IOException("Failed, i can't deleate a direcotry: " + f);
        }
        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + f);
        }
    }

    public static String hash256(byte[] input) {
        try {
            //Base64 b64enc = new Base64();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //set hashing provider
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            MessageDigest digest = MessageDigest.getInstance("SHA3-256", BouncyCastleProvider.PROVIDER_NAME);
            digest.reset();
            ByteBuffer bb = ByteBuffer.wrap(input);
            digest.update(bb);
            Hex.encode(digest.digest(), baos);
            bb.clear();
            String out = baos.toString("UTF-8");
            baos.close();
            return out;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * convert pattern from {@code Log.getLogFilePattern()} to path
     *
     * @param pattern
     * @return
     */
    public static Path logFile(String pattern) throws Exception {
        return Paths.get(getDefaultLogsDirectoryPath().toString(), pattern);
    }
    
    /**
     *
     * @return default logs directory path
     */
    public static Path getDefaultLogsDirectoryPath() throws Exception {
        return Paths.get(getDefaultApplicationDirectoryPath().toString(),
                "logs");
    }
    
    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try ( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultStringBuilder.append(line);
                resultStringBuilder.append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
    
}
