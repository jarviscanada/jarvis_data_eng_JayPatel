package ca.jrvs.apps.grep;

public interface RegexExc {

    /**
     * return true if fielextention is jpg or jpeg
     * @param filename
     * @return
     */
    public boolean matchJpeg(String filename);

    /**
     * return true if ip is valid
     * @param ip
     * @return
     */
    public  boolean matchIp(String ip);

    /**
     * return true if line is empty
     * @param line
     * @return
     */
    public boolean isEmptyLine(String line);
}
