package Screen.ClassRoom;

import Models.Classroom;
import Screen.AbstractScreen;
import Services.ClassroomService;
import java.util.Optional;

public class ListClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public ListClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        DANH SÁCH TẤT CẢ LỚP HỌC          │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        // Sử dụng service để hiển thị danh sách
        classroomService.displayAllClasses();
        
        System.out.println("\nTùy chọn:");
        System.out.println("1. Xem thông tin chi tiết của một lớp học");
        System.out.println("0. Quay lại");
        
        int choice = inputInt("\nNhập lựa chọn của bạn: ");
        
        if (choice == 1 && classroomService.getTotalClasses() > 0) {
            viewDetails();
        }
    }

    private void viewDetails() {
        String classId = input("\nNhập Mã Lớp để xem chi tiết: ");
        Optional<Classroom> classroomOpt = classroomService.findById(classId);
        
        if (!classroomOpt.isPresent()) {
            System.out.println("Không tìm thấy lớp học!");
        } else {
            Classroom classroom = classroomOpt.get();
            System.out.println("\n" + "=".repeat(80));
            System.out.println("CHI TIẾT LỚP HỌC");
            System.out.println("=".repeat(80));
            System.out.println("Mã Lớp: " + classroom.getClassId());
            System.out.println("Tên Lớp: " + classroom.getClassName());
            System.out.println("Năm Học: " + classroom.getSchoolYear());
            System.out.println("Niên Khóa: " + classroom.getCourse());
            System.out.println("=".repeat(80));
        }
        
        pause();
    }
}