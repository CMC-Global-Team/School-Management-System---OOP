package Screen.ClassRoom;

import Models.Classroom;
import Screen.AbstractScreen;
import Services.ClassroomService;
import java.util.Optional;

public class DeleteClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public DeleteClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│               XÓA LỚP HỌC                │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        if (classroomService.getTotalClasses() == 0) {
            System.out.println("\nKhông có lớp học nào để xóa.");
            pause();
            return;
        }

        String classId = input("\nNhập Mã Lớp cần xóa: ");
        
        Optional<Classroom> classroomOpt = classroomService.findById(classId);
        
        if (!classroomOpt.isPresent()) {
            System.out.println("Lỗi: Không tìm thấy lớp học!");
            pause();
            return;
        }

        Classroom classroom = classroomOpt.get();
        System.out.println("\nĐã tìm thấy lớp học:");
        System.out.println(classroom);
        
        String confirm = input("\nBạn có chắc chắn muốn xóa lớp học này? (yes/no): ");
        
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            classroomService.deleteClass(classId);
        } else {
            System.out.println("\nĐã hủy xóa.");
        }
        
        pause();
    }
}