package Models;

/**
 * Interface cơ bản cho tất cả các Entity trong hệ thống
 * Định nghĩa các phương thức cần thiết để serialize/deserialize
 */
public interface IEntity {
    /**
     * Lấy ID duy nhất của entity
     */
    String getId();
    
    /**
     * Chuyển đổi entity thành chuỗi để lưu vào file
     * Format: các trường cách nhau bởi dấu phẩy
     */
    String toFileString();
    
    /**
     * Validate entity trước khi thực hiện các thao tác
     */
    boolean validate();
}
