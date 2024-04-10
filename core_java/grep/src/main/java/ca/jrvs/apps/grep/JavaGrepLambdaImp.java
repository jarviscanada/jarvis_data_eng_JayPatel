package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp{


    public static void main(String[] args) {

        if(args.length < 3)
        {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootpath outfile");
        }

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);


        try {
            javaGrepLambdaImp.process();
        } catch (Exception e) {
            javaGrepLambdaImp.logger.error("Errro: UNable to process", e);
        }
    }

    /**
     * Impleted using lamda and stream API
     * @param inputFile
     * @return
     */
    @Override
    public List<String> readLine(File inputFile) {

        try{
            Stream<String>  stream = Files.lines(inputFile.toPath());
            return stream.collect(Collectors.toList());
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Impleted using lamda and stream API
     * @param rootDir
     * @return
     */
    @Override
    public List<File> listFiles(String rootDir) {


        File dir = new File(rootDir);
        if (dir.isDirectory()) {
            try {
                return Files.walk(dir.toPath()).filter(Files::isRegularFile).map(path -> path.toFile()).collect(Collectors.toList());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else
        {
            return super.listFiles(rootDir);
        }
    }
}
