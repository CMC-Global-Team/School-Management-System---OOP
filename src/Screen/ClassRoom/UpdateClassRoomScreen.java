package Screen.ClassRoom;

import Models.Classroom;
import Screen.AbstractScreen;
import Services.ClassroomService;
import java.util.Optional;

public class UpdateClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public UpdateClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│            CẬP NHẬT LỚP HỌC              │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        if (classroomService.getTotalClasses() == 0) {
            System.out.println("\nKhông có lớp học nào để cập nhật.");
            pause();
            return;
        }

        String classId = input("\nNhập Mã Lớp cần cập nhật: ");
        
        Optional<Classroom> classroomOpt = classroomService.findById(classId);
        
        if (!classroomOpt.isPresent()) {
            System.out.println("Lỗi: Không tìm thấy lớp học!");
            pause();
            return;
        }
        
        Classroom classroom = classroomOpt.get();

        System.out.println("\nThông tin hiện tại:");
        System.out.println(classroom);
        
        System.out.println("\nBạn muốn cập nhật thông tin gì?");
        System.out.println("1. Tên Lớp");
        System.out.println("2. Năm Học");
        System.out.println("3. Niên Khóa");
        System.out.println("4. Cập nhật Tất cả");
        System.out.println("0. Hủy");
        
        int choice = inputInt("\nNhập lựa chọn của bạn: ");
        
        switch (choice) {
            case 1:
                updateClassName(classroom);
                break;
            case 2:
                updateSchoolYear(classroom);
                break;
            case 3:
                updateCourse(classroom);
                break;
            case 4:
                updateAll(classroom);
                break;
            case 0:
                System.out.println("Đã hủy cập nhật.");
                pause();
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                pause();
                return;
        }
        
        // Sử dụng service để cập nhật
        if (classroomService.updateClass(classroom)) {
            System.out.println("\nThông tin đã cập nhật:");
            System.out.println(classroom);
        }
        pause();
    }

    private void updateClassName(Classroom classroom) {
        String newName = input("Nhập Tên Lớp mới (hiện tại: " + classroom.getClassName() + "): ");
        if (!newName.isEmpty()) {
            classroom.setClassName(newName);
        }
    }

    private void updateSchoolYear(Classroom classroom) {
        String newYear = input("Nhập Năm Học mới (hiện tại: " + classroom.getSchoolYear() + "): ");
        if (!newYear.isEmpty()) {
            classroom.setSchoolYear(newYear);
        }
    }

    private void updateCourse(Classroom classroom) {
        String newCourse = input("Nhập Niên Khóa mới (hiện tại: " + classroom.getCourse() + "): ");
        if (!newCourse.isEmpty()) {
            classroom.setCourse(newCourse);
        }
    }

    private void updateAll(Classroom classroom) {
        updateClassName(classroom);
        updateSchoolYear(classroom);
        updateCourse(classroom);
    }
}