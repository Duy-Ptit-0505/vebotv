package six.sportswears.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadHtml {
    public static String readHtml(String url) {
        try {
            // Đọc nội dung của file index.html
            Path path = Paths.get(url);
            return Files.readString(path);
        } catch (IOException e) {
            // Xử lý lỗi nếu có
            return "Error reading index.html";
        }
    }
}
