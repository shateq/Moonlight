package shateq.java.moonlight;

import java.io.IOException;
import java.util.jar.Manifest;

public final class Moonlight {
    public static final int NORMAL = 0xfffda6; // Casual color
    public static final int BAD = 0xff776b; // Bad color
    public static final String GITHUB_URL = "https://github.com/shateq/Moonlight";
    public static final String USER_AGENT = "Moonlight/@version@/JDA (" + GITHUB_URL + ")";
    public static String VERSION = null;

    static {
        try {
            VERSION = getVersion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getVersion() throws IOException {
        final Manifest mf = new Manifest();
        mf.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
        return mf.getMainAttributes().getValue("Implementation-Version");
    }
}