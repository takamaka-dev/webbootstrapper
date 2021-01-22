/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.takamakachain.walletweblauncher.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.NotificationBroadcasterSupport;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.ignite.internal.util.IgniteUtils;
import org.json.JSONObject;

/**
 *
 * @author isacco
 */
public class CommandTakamakaWebApp extends AbstractGenericCommand {

    String manual = null;

    private static Path webapp;
    private static Path jdkPath;
    private Options opt;
    private DefaultParser par;
    private CommandLine cmd;
    private String suffix, arch;
    private static JSONObject jsonIntegrity;
    private static Path libraryJdkPath;
    private static String jdkHash;
    private static Path libraryPayaraPath;
    private static String payaraHash;
    private static Path libraryWebAppPath;
    private static String webAppHash;

    public CommandTakamakaWebApp(String[] args,
            NotificationBroadcasterSupport nbs,
            long sequence) throws IOException {
        super(args, nbs, sequence);

        //FileHelper.initProjectFiles();
        opt = new Options();
        opt.addOption(Option
                .builder("s")
                .hasArg(false)
                .longOpt("start")
                .required(false)
                .desc("Start service")
                .build());
        opt.addOption(
                Option
                        .builder("u")
                        .hasArg(false)
                        .longOpt("update")
                        .required(false)
                        .desc("Update service")
                        .build());
        opt.addOption(
                Option
                        .builder("a")
                        .hasArg(false)
                        .longOpt("accept-eula")
                        .required(false)
                        .desc("Accept EULA to proceed")
                        .build());

        par = new DefaultParser();
        try {
            cmd = par.parse(opt, args, true);
            List<String> argList = cmd.getArgList();
            if (!argList.isEmpty()) {
                throw new ParseException("not correct syntax written.");
            }
        } catch (ParseException ex) {
        }
    }

    @Override
    public void execute() {
        try {
            if (cmd.hasOption("a")) {
                startWebApp(cmd);
            } else {
                Scanner sc = new Scanner(System.in); //System.in is a standard input stream.
                System.out.print("Do you accept EULA Terms Service? (Y/N)");
                if (sc.nextLine().equals("Y")) {
                    startWebApp(cmd);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(CommandTakamakaWebApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startWebApp(CommandLine cmd) throws Exception {
        initCommandVariables();
        checkAndDownloadFiles(cmd);
        startSecondJVM();
    }

    public void startSecondJVM() throws Exception {
        Path defaultApplicationDirectoryPath = FileHelper.getDefaultApplicationDirectoryPath();
        Path jdkPath = Paths.get(defaultApplicationDirectoryPath.toString(), "jdk", "jdk-" + suffix + "-" + arch + "_bin", "bin", "java");

        File file = new File(defaultApplicationDirectoryPath.toString() + "/jdk/jdk-" + suffix + "-" + arch + "_bin/bin/java");

        if (file.exists()) {
            System.out.println("Is Execute allow : " + file.canExecute());
            System.out.println("Is Write allow : " + file.canWrite());
            System.out.println("Is Read allow : " + file.canRead());
        }

        file.setExecutable(true);

        Path warPath = Paths.get(defaultApplicationDirectoryPath.toString(), "webapp", "walletweb-1.0-SNAPSHOT.war");
        Path payaraPath = Paths.get(defaultApplicationDirectoryPath.toString(), "webapp", "payara-micro-5.2020.7.jar");

        Runtime runtime = Runtime.getRuntime();

        String[] arr = new String[]{jdkPath.toString(), "-jar", payaraPath.toString(), "--rootDir", "/tmp/py",  "--deploy", warPath.toString()};
        System.out.println(arr[0]);

        Process exec = runtime.exec(arr);

        new Thread(new SyncPipe(exec.getErrorStream(), System.out)).start();
        new Thread(new SyncPipe(exec.getInputStream(), System.out)).start();

    }

    private void downloadResource(String resourceName, Path destinationPath) throws MalformedURLException, IOException {
        try {
            URL url = new URL(resourceName);
            HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
            long completeFileSize = httpConnection.getContentLength();

            try ( java.io.BufferedInputStream resourceAsStream = new java.io.BufferedInputStream(httpConnection.getInputStream())) {
                Path resourcePath = Paths.get(destinationPath.toString());
                System.out.println(resourcePath.toFile().toString());

                File file = new File(resourcePath.toFile().toString());
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);

                java.io.FileOutputStream fos = new java.io.FileOutputStream(
                        resourcePath.toFile().toString());
                try ( java.io.BufferedOutputStream bout = new BufferedOutputStream(
                        fos, 1024)) {
                    byte[] data = new byte[1024];
                    long downloadedFileSize = 0;
                    int x;
                    while ((x = resourceAsStream.read(data, 0, 1024)) >= 0) {
                        downloadedFileSize += x;

                        bout.write(data, 0, x);
                    }
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }

    class SyncPipe implements Runnable {

        public SyncPipe(InputStream istrm, OutputStream ostrm) {
            istrm_ = istrm;
            ostrm_ = ostrm;
        }

        public void run() {
            try {
                final byte[] buffer = new byte[1024];
                for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
                    ostrm_.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private final OutputStream ostrm_;
        private final InputStream istrm_;
    }

    public void checkAndDownloadFiles(CommandLine cmd) throws Exception {
        String jsonUrl = "https://downloads.takamaka.dev/integrityCheckSumReleasesWebApp.json";

        String data = "";
        try {
            URL urlObject = new URL(jsonUrl);
            URLConnection urlConnection = urlObject.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            data = FileHelper.readFromInputStream(inputStream);
        } catch (IOException ex) {

        }

        jsonIntegrity = new JSONObject(data);
        System.out.println(jsonIntegrity.toString());

        Path defaultApplicationDirectoryPath = FileHelper.getDefaultApplicationDirectoryPath();

        libraryJdkPath = Paths.get(defaultApplicationDirectoryPath.toString(), "jdk", "jdk-" + suffix + "-" + arch + "_bin.zip");
        jdkHash = libraryJdkPath.toFile().exists() ? FileHelper.hash256(FileUtils.readFileToByteArray(libraryJdkPath.toFile())) : "";

        if (!libraryJdkPath.toFile().exists()
                || (cmd.hasOption("u") && !jdkHash.equals(jsonIntegrity.getJSONObject("jdk").getJSONObject(suffix).getJSONObject(arch).get("hash").toString()))) {
            downloadResource(
                    jsonIntegrity.getJSONObject("jdk").getJSONObject(suffix).getJSONObject(arch).get("url").toString(),
                    Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "jdk", "jdk-" + suffix + "-" + arch + "_bin.zip")
            );
        }
        IgniteUtils.unzip(Paths.get(jdkPath.toString(), "jdk-" + suffix + "-" + arch + "_bin.zip").toFile(), jdkPath.toFile(), null);

        libraryPayaraPath = Paths.get(defaultApplicationDirectoryPath.toString(), "webapp", "payara-micro-5.2020.7.jar");
        payaraHash = libraryPayaraPath.toFile().exists() ? FileHelper.hash256(FileUtils.readFileToByteArray(libraryPayaraPath.toFile())) : "";
        
        if (!libraryPayaraPath.toFile().exists()
                || (cmd.hasOption("u") && !payaraHash.equals(jsonIntegrity.getJSONObject("payara").get("hash").toString()))) {
            downloadResource(
                    jsonIntegrity.getJSONObject("payara").get("url").toString(),
                    Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "webapp", "payara-micro-5.2020.7.jar")
            );
        }

        libraryWebAppPath = Paths.get(defaultApplicationDirectoryPath.toString(), "webapp", "walletweb-1.0-SNAPSHOT.war");
        webAppHash = libraryWebAppPath.toFile().exists() ? FileHelper.hash256(FileUtils.readFileToByteArray(libraryWebAppPath.toFile())) : "";
        
        System.out.println(webAppHash);
        System.out.println("remote webapp hash: " + jsonIntegrity.getJSONObject("webapp").get("hash").toString());
        
        if (!libraryWebAppPath.toFile().exists()
                || (cmd.hasOption("u") && !webAppHash.equals(jsonIntegrity.getJSONObject("webapp").get("hash").toString()))) {
            downloadResource(
                    jsonIntegrity.getJSONObject("webapp").get("url").toString(),
                    Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "webapp", "walletweb-1.0-SNAPSHOT.war")
            );
        }
    }

    public void initCommandVariables() throws Exception {
        switch (System.getProperty("os.name")) {
            case "Mac OS X":
                suffix = "mac";
                break;
            case "Linux":
                suffix = "linux";
                break;
            default:
                suffix = "windows";
                break;
        }

        boolean is64bit;
        if (suffix.equals("windows")) {
            is64bit = (System.getenv("ProgramFiles(x86)") != null);
        } else {
            is64bit = (System.getProperty("os.arch").contains("64"));
        }

        if (is64bit) {
            arch = "x64";
        }

        webapp = Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "webapp");
        jdkPath = Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "jdk");

        try {
            FileHelper.initProjectFiles();
            //FileHelper.deleteFolderContent(jdkPath.toFile());
            if (!FileHelper.directoryExists(jdkPath)) {
                FileHelper.createDir(jdkPath);
            }
            //FileHelper.deleteFolderContent(webapp.toFile());
            if (!FileHelper.directoryExists(webapp)) {
                FileHelper.createDir(webapp);
            }

        } catch (IOException ex) {
            Logger.getLogger(CommandTakamakaWebApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
