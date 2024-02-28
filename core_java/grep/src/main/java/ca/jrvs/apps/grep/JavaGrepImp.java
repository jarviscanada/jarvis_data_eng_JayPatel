package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public void process() throws IOException {

        List<String> matcheLines = new ArrayList<>();
        for (File file : listFiles(getRootPath())) {
            for(String line : readLine(file))
            {
                if(containsPatten(line))
                {
                    matcheLines.add(line);
                }
            }
        }

        writeToFile(matcheLines);

    }

    public List<File> listFiles(String rootDir) {

        List<File> files = new ArrayList<>();
        File dir = new File(rootDir);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                Collections.addAll(files, dir.listFiles());
            }
        }
        return files;
    }

    public List<String> readLine(File inputFile) {
        try {
            return Files.readAllLines(inputFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean containsPatten(String line) {

        return Pattern.matches(getRegex(),line);
    }

    public void writeToFile(List<String> lines) throws IOException {
        File myObj = new File(getOutFile());
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
            FileWriter myWriter = new FileWriter(getOutFile());
            for(String line : lines)
            {
                myWriter.write(line + '\n');
            }
            myWriter.close();
        } else {
            System.out.println("File already exists.");
        }

    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
    this.regex = regex;
    }

    public String getOutFile() {
        return outFile;
    }

    public void setOutFile(String outFile) {
    this.outFile = outFile;
    }


    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootpath outfile");
        }

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);


        try {
            javaGrepImp.process();
        } catch (Exception e) {
            javaGrepImp.logger.error("Errro: UNable to process", e);
        }
    }
}
