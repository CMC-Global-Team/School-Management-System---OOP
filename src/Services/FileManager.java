package Services;

import java.io.File;

/**
 * FileManager - Quản lý đường dẫn file theo OOP
 * Tập trung quản lý tất cả file paths của hệ thống
 */
public class FileManager {
    
    // Thư mục gốc chứa data
    private static final String DATA_DIR = "data";
    
    // File paths cho từng entity
    public static final String CLASSROOM_FILE = DATA_DIR + "/classrooms.txt";
    public static final String STUDENT_FILE = DATA_DIR + "/students.txt";
    public static final String TEACHER_FILE = DATA_DIR + "/teachers.txt";
    public static final String SUBJECT_FILE = DATA_DIR + "/subjects.txt";
    public static final String GRADE_FILE = DATA_DIR + "/grades.txt";
    public static final String TUITION_FILE = DATA_DIR + "/tuitions.txt";
    public static final String TEACHING_ASSIGNMENT_FILE = DATA_DIR + "/teaching_assignments.txt";
    
    /**
     * Khởi tạo thư mục data nếu chưa tồn tại
     */
    public static void initializeDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            boolean created = dataDir.mkdirs();
            if (created) {
                System.out.println("Đã tạo thư mục data: " + DATA_DIR);
            }
        }
    }
    
    /**
     * Kiểm tra và tạo file nếu chưa tồn tại
     * @param filePath Đường dẫn file
     */
    public static void ensureFileExists(String filePath) {
        initializeDataDirectory();
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Đã tạo file: " + filePath);
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tạo file " + filePath + ": " + e.getMessage());
            }
        }
    }
    

}