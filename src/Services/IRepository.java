package Services;

import java.util.List;
import java.util.Optional;

/**
 * Generic Repository Interface - Định nghĩa các thao tác CRUD cơ bản
 * Có thể tái sử dụng cho bất kỳ entity nào
 * @param <T> Loại entity
 */
public interface IRepository<T> {

    /**
     * Thêm một entity mới
     * @param entity Entity cần thêm
     * @return true nếu thành công, false nếu thất bại
     */
    boolean add(T entity);

    /**
     * Cập nhật một entity đã tồn tại
     * @param entity Entity cần cập nhật
     * @return true nếu thành công, false nếu thất bại
     */
    boolean update(T entity);

    /**
     * Xóa entity theo ID
     * @param id ID của entity cần xóa
     * @return true nếu thành công, false nếu thất bại
     */
    boolean delete(String id);

    /**
     * Tìm entity theo ID
     * @param id ID của entity
     * @return Optional chứa entity nếu tìm thấy, rỗng nếu không
     */
    Optional<T> findById(String id);

    /**
     * Lấy tất cả entities
     * @return Danh sách tất cả entities
     */
    List<T> findAll();

    /**
     * Tìm kiếm entities theo từ khóa
     * @param keyword Từ khóa tìm kiếm
     * @return Danh sách entities phù hợp
     */
    List<T> search(String keyword);

    /**
     * Kiểm tra xem ID đã tồn tại chưa
     * @param id ID cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     */
    boolean exists(String id);

    /**
     * Đếm tổng số entities
     * @return Số lượng entities
     */
    int count();

    /**
     * Lưu tất cả thay đổi vào file
     * @return true nếu thành công, false nếu thất bại
     */
    boolean saveToFile();

    /**
     * Tải dữ liệu từ file
     * @return true nếu thành công, false nếu thất bại
     */
    boolean loadFromFile();
}