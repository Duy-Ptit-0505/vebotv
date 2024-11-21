package six.sportswears.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {
    public static String convertDate(Date date) {
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd MMM yyyy");
        return outputFormatter.format(date);
    }
}
