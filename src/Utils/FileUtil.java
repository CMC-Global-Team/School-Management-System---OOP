package Utils;

import java.io.*;
import java.util.ArrayList;
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

    public static List<String> readLines(String filepath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);

            }
            return lines;
        }
    }
    // Kiểm tra file tồn tại không
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }
}
