package six.sportswears.utils;

public class StringUtils {
    public static boolean check(String a) {
        if(a == null || a.equals("")) return false;
        return true;
    }
    public static String substringBeforeLast(String path, String delimiter) {
        if (path == null || path.isEmpty() || delimiter == null || delimiter.isEmpty()) {
            return path;
        }

        int lastIndex = path.lastIndexOf(delimiter);
        if (lastIndex == -1) {
            return path;
        }

        return path.substring(0, lastIndex);
    }
}