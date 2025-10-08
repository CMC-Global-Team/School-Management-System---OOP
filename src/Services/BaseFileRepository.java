package Services;

import Models.IEntity;
import Utils.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Abstract Base Repository cho file-based storage
 * Implement các thao tác CRUD cơ bản, các class con chỉ cần implement phần đặc thù
 * @param <T> Loại entity phải implement IEntity
 */
public abstract class BaseFileRepository<T extends IEntity> implements IRepository<T> {
    
    protected List<T> entities;
    protected String filePath;
    
    public BaseFileRepository(String filePath) {
        this.filePath = filePath;
        this.entities = new ArrayList<>();
        // Load data from file
        this.loadData();
    }
    
    // Wrapper method để tránh overridable method call in constructor
    private void loadData() {
        loadFromFile();
    }
    
    @Override
    public boolean add(T entity) {
        if (entity == null || !entity.validate()) {
            return false;
        }
        
        if (exists(entity.getId())) {
            return false; // ID đã tồn tại
        }
        
        entities.add(entity);
        return saveToFile();
    }
    
    @Override
    public boolean update(T entity) {
        if (entity == null || !entity.validate()) {
            return false;
        }
        
        Optional<T> existingEntity = findById(entity.getId());
        if (!existingEntity.isPresent()) {
            return false; // Không tìm thấy entity để update
        }
        
        // Xóa entity cũ và thêm entity mới
        entities.removeIf(e -> e.getId().equals(entity.getId()));
        entities.add(entity);
        return saveToFile();
    }
    
    @Override
    public boolean delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        
        boolean removed = entities.removeIf(e -> e.getId().equalsIgnoreCase(id));
        if (removed) {
            return saveToFile();
        }
        return false;
    }
    
    @Override
    public Optional<T> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return entities.stream()
                .filter(e -> e.getId().equalsIgnoreCase(id))
                .findFirst();
    }
    
    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }
    
    @Override
    public boolean exists(String id) {
        return findById(id).isPresent();
    }
    
    @Override
    public int count() {
        return entities.size();
    }
    
    @Override
    public boolean saveToFile() {
        try {
            List<String> lines = entities.stream()
                    .map(IEntity::toFileString)
                    .collect(Collectors.toList());
            
            FileUtil.writeLines(filePath, lines);
            return true;
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu file: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean loadFromFile() {
        try {
            if (!FileUtil.fileExists(filePath)) {
                // File chưa tồn tại, tạo danh sách rỗng
                entities = new ArrayList<>();
                return true;
            }
            
            List<String> lines = FileUtil.readLines(filePath);
            entities = lines.stream()
                    .map(this::parseFromString)
                    .filter(e -> e != null)
                    .collect(Collectors.toList());
            
            return true;
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
            entities = new ArrayList<>();
            return false;
        }
    }
    
    /**
     * Phương thức abstract để parse string thành entity
     * Mỗi class con phải implement cách parse riêng của nó
     * @param line Chuỗi từ file
     * @return Entity được parse, hoặc null nếu lỗi
     */
    protected abstract T parseFromString(String line);
    
    /**
     * Phương thức abstract để tìm kiếm
     * Mỗi entity có cách tìm kiếm khác nhau
     * @param entity Entity cần kiểm tra
     * @param keyword Từ khóa tìm kiếm
     * @return true nếu match, false nếu không
     */
    protected abstract boolean matchesKeyword(T entity, String keyword);
    
    @Override
    public List<T> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        
        String lowerKeyword = keyword.toLowerCase();
        return entities.stream()
                .filter(e -> matchesKeyword(e, lowerKeyword))
                .collect(Collectors.toList());
    }
}
