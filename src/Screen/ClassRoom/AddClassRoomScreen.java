package Screen.ClassRoom;

import Screen.AbstractScreen;
import Services.ClassroomService;

public class AddClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public AddClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│            THÊM LỚP HỌC MỚI              │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nNhập thông tin lớp học:");
        
        String classId = input("Mã Lớp: ");
        String className = input("Tên Lớp: ");
        String schoolYear = input("Năm Học (vd: 2024-2025): ");
        String course = input("Niên Khóa (vd: 2020-2024): ");
        
        // Sử dụng service để thêm lớp học
        if (classroomService.addClass(classId, className, schoolYear, course)) {
            System.out.println("\nThông tin lớp học đã thêm:");
            classroomService.findById(classId).ifPresent(System.out::println);
        }
        
        pause();
    }
}