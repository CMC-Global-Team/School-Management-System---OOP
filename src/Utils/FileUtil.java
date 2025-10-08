package Utils;

import java.io.*;
import java.util.List;

public class FileUtil {
    // Ghi danh sách String vào file
    public static void writeLines(String filePath, List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

}