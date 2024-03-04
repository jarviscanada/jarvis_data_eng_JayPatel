package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {
    /**
     * Top level search flow
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Traverse a given directory and return all files
     * @param rootDir
     * @return
     */
    List<File> listFiles(String rootDir);


    /**
     * Read a file and return all the lines
     *
     * Explain fileReaderm bufferReader and character encoding
     * @param inputFile
     * @return
     */
    List<String> readLine(File inputFile);

    /**
     * check if a line contains  the rejex patten(passed by user)
     * @param line
     * @return
     */
    boolean containsPatten(String line);

    /**
     * Write a lines ti files
     *
     * Explore : fileOutputStream , outputStramWriter and BuffereadWriter
     * @param lines
     * @throws IOException
     */
    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();
    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);
}
