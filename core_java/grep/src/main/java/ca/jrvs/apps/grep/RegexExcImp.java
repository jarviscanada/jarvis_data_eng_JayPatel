package ca.jrvs.apps.grep;

import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc{

    /**
     * return true if fielextention is jpg or jpeg
     *
     * @param filename
     * @return
     */
    public boolean matchJpeg(String filename) {
        String rejex = "^.*\\.(jpg|jpeg)$";
        return Pattern.matches(rejex,filename);
    }

    /**
     * return true if ip is valid
     *
     * @param ip
     * @return
     */
    public boolean matchIp(String ip) {
        return Pattern.matches("/^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$/gm",ip);
    }

    /**
     * return true if line is empty
     *
     * @param line
     * @return
     */
    public boolean isEmptyLine(String line) {
        return Pattern.matches("/^\\s*$/",line);
    }

    public static void main(String[] args) {
        RegexExc rexObj = new RegexExcImp();

        System.out.println(rexObj.matchJpeg("asdasds.jpddg"));

    }


}
