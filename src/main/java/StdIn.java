import java.util.NoSuchElementException;
import java.util.Scanner;

public final class StdIn {

    private static final String CHARSET_NAME = "UTF-8";

    private static final Scanner scanner = new Scanner(new java.io.BufferedInputStream(System.in), CHARSET_NAME);

    private StdIn() {
    }

    public static boolean isEmpty() {
        return !scanner.hasNext();
    }

    public static String readString() {
        try {
            return scanner.next();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Attempts to read a 'String' value from standard input, but no more tokens are available.");
        }
    }
}
