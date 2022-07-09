import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public final class StdOut {

    private static final String CHARSET_NAME = "UTF-8";

    private static PrintWriter out;

    static {
        try {
            out = new PrintWriter(new OutputStreamWriter(System.out, CHARSET_NAME), true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void println(Object x) {
        out.println(x);
    }
}