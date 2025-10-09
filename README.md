# HỆ THỐNG QUẢN LÝ TRƯỜNG HỌC
## School Management System - OOP

### 📋 Mô tả dự án
Hệ thống quản lý trường học được phát triển bằng Java với thiết kế hướng đối tượng (OOP), sử dụng các design patterns như Singleton, Repository Pattern và Abstract Factory. Hệ thống cung cấp đầy đủ các chức năng quản lý học sinh, giáo viên, lớp học, môn học, điểm số và học phí.

### 🎯 Tính năng chính

#### 1. **Quản Lý Lớp Học**
- Thêm, sửa, xóa lớp học
- Tìm kiếm lớp học theo từ khóa
- Hiển thị danh sách tất cả lớp học
- Quản lý thông tin lớp: mã lớp, tên lớp, năm học, niên khóa

#### 2. **Quản Lý Môn Học**
- Thêm, sửa, xóa môn học
- Tìm kiếm môn học
- Danh sách tất cả môn học
- Phân công giáo viên cho môn học
- Chỉnh sửa từng trường thông tin riêng lẻ
- Hướng dẫn sử dụng chi tiết cho từng chức năng

#### 3. **Quản Lý Học Sinh**
- Thêm, sửa, xóa học sinh
- Tìm kiếm học sinh theo nhiều tiêu chí
- Lọc học sinh theo lớp, trạng thái, giới tính
- **Chuyển lớp cho học sinh** (tính năng mới)
- Xuất danh sách học sinh theo nhiều định dạng
- Thống kê học sinh từ cả classrooms.txt và students.txt

#### 4. **Quản Lý Giáo Viên**
- Thêm, sửa, xóa giáo viên
- Tìm kiếm giáo viên
- Danh sách tất cả giáo viên
- Quản lý thông tin cá nhân và chuyên môn

#### 5. **Quản Lý Điểm Số**
- Nhập điểm cho học sinh
- Xem điểm theo lớp, môn học
- Tính điểm trung bình
- Xuất báo cáo điểm

#### 6. **Quản Lý Học Phí**
- Tính học phí theo lớp
- Quản lý thanh toán
- Xuất hóa đơn học phí
- Thống kê thu chi

### 🏗️ Kiến trúc hệ thống

#### **Design Patterns được sử dụng:**
- **Singleton Pattern**: Đảm bảo chỉ có một instance của Service classes
- **Repository Pattern**: Tách biệt business logic và data access
- **Abstract Factory**: Tạo các entity objects
- **Template Method**: AbstractScreen cho các màn hình

#### **Cấu trúc thư mục:**
```
src/
├── Models/                 # Các entity classes
│   ├── Student.java
│   ├── Teacher.java
│   ├── Classroom.java
│   ├── Subject.java
│   ├── Grade.java
│   └── Tuition.java
├── Services/               # Business logic layer
│   ├── StudentService.java
│   ├── TeacherService.java
│   ├── ClassroomService.java
│   ├── SubjectService.java
│   ├── GradeService.java
│   ├── TuitionService.java
│   ├── BaseFileRepository.java
│   └── StudentRepository.java
├── Screen/                 # Presentation layer
│   ├── AbstractScreen.java
│   ├── DashBoard.java
│   ├── Student/
│   ├── Teacher/
│   ├── Classroom/
│   ├── Subject/
│   ├── Grade/
│   └── Tuition/
└── Utils/                  # Utility classes
    ├── FileUtil.java
    ├── InputUtil.java
    └── FileManager.java
```

### 📁 Cấu trúc dữ liệu

#### **File dữ liệu:**
- `data/students.txt`: Thông tin học sinh
- `data/teachers.txt`: Thông tin giáo viên
- `data/classrooms.txt`: Thông tin lớp học
- `data/subjects.txt`: Thông tin môn học
- `data/grades.txt`: Điểm số học sinh
- `data/tuitions.txt`: Thông tin học phí

#### **Format dữ liệu:**
```
students.txt: ID,Tên,Ngày sinh,Giới tính,Lớp,Khóa,SĐT,Địa chỉ,Trạng thái
teachers.txt: ID,Tên,Ngày sinh,Giới tính,Chuyên môn,SĐT,Địa chỉ,Trạng thái
classrooms.txt: Mã lớp,Tên lớp,Năm học,Niên khóa
subjects.txt: Mã môn,Tên môn,Số tiết,Khóa,Mô tả,Giáo viên phụ trách
```

### 🚀 Hướng dẫn cài đặt và chạy

#### **Yêu cầu hệ thống:**
- Java 8 trở lên
- IDE: IntelliJ IDEA, Eclipse, hoặc VS Code
- Hệ điều hành: Windows, macOS, Linux

#### **Cách chạy:**
1. Clone repository về máy
2. Mở project trong IDE
3. Compile và chạy file `Main.java`
4. Sử dụng menu để điều hướng

#### **Cấu trúc menu chính:**
```
┌──────────────────────────────────────────┐
│        HỆ THỐNG QUẢN LÝ TRƯỜNG HỌC        │
├──────────────────────────────────────────┤
│  1. Quản Lý Lớp Học                       │
│  2. Quản Lý Môn Học                       │
│  3. Quản Lý Điểm                          │
│  4. Quản Lý Học Sinh                      │
│  5. Quản Lý Giáo Viên                     │
│  6. Quản Lý Học Phí                       │
│  0. Thoát Chương Trình                    │
└──────────────────────────────────────────┘
```

### ✨ Tính năng nổi bật

#### **1. Chuyển lớp học sinh**
- Chuyển học sinh từ lớp này sang lớp khác
- Đảm bảo đồng nhất dữ liệu trong file
- Hiển thị thông tin trước và sau khi chuyển
- Thống kê số học sinh trong từng lớp

#### **2. Xuất danh sách thông minh**
- Xuất theo lớp với thống kê từ cả classrooms.txt và students.txt
- Hiển thị danh sách lớp đầy đủ
- Thông tin chi tiết về nguồn gốc của lớp
- Validation lớp tồn tại

#### **3. Chỉnh sửa môn học linh hoạt**
- Chỉnh sửa từng trường thông tin riêng lẻ
- Không cần nhập lại toàn bộ thông tin
- Menu số để chọn trường cần sửa
- Hiển thị danh sách giáo viên khi phân công

#### **4. Giao diện thân thiện**
- Menu tiếng Việt có dấu
- Hướng dẫn sử dụng chi tiết
- Không sử dụng icon (theo yêu cầu)
- Validation đầu vào đầy đủ

### 🔧 Công nghệ sử dụng

- **Ngôn ngữ**: Java 8+
- **Paradigm**: Object-Oriented Programming (OOP)
- **Design Patterns**: Singleton, Repository, Abstract Factory, Template Method
- **File I/O**: Text-based file storage
- **Data Structure**: Collections Framework (List, Map, Optional)
- **Stream API**: Java 8 Streams cho data processing

### 📊 Thống kê dự án

- **Tổng số file**: 50+ files
- **Tổng số dòng code**: 5000+ lines
- **Số entity classes**: 6 classes
- **Số service classes**: 6 classes
- **Số screen classes**: 20+ classes
- **Design patterns**: 4 patterns

### 🎓 Học tập và phát triển

#### **Kiến thức áp dụng:**
- Object-Oriented Programming principles
- Design Patterns implementation
- File I/O operations
- Exception handling
- Data validation
- User interface design
- Business logic separation

#### **Kỹ năng phát triển:**
- Clean code practices
- Modular architecture
- Error handling
- User experience design
- Data consistency
- Performance optimization

### 📝 Ghi chú phát triển

#### **Phiên bản hiện tại**: 1.0.0
#### **Cập nhật gần nhất**:
- ✅ Thêm chức năng chuyển lớp học sinh
- ✅ Cập nhật xuất danh sách từ cả hai nguồn dữ liệu
- ✅ Cải tiến giao diện chỉnh sửa môn học
- ✅ Thêm hướng dẫn sử dụng chi tiết
- ✅ Đảm bảo đồng nhất dữ liệu

#### **Tính năng trong tương lai**:
- 🔄 Backup và restore dữ liệu
- 🔄 Báo cáo thống kê nâng cao
- 🔄 Giao diện web
- 🔄 Database integration
- 🔄 Multi-user support

### 👥 Đóng góp

Dự án này được phát triển như một bài tập học tập về OOP và Design Patterns. Mọi đóng góp và phản hồi đều được chào đón!

### 📄 License

Dự án này được phát triển cho mục đích học tập và nghiên cứu.

---

**Tác giả**: Winni  
**Ngày tạo**: 2024  
**Cập nhật cuối**: 2024