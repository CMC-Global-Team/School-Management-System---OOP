package Screen.Subject;

import Screen.AbstractScreen;
import Models.Subject;
import Models.Teacher;
import Services.SubjectService;
import Services.TeacherService;

import java.util.*;

/**
 * Màn hình gán giáo viên cho môn học
 */
public class AssignTeacherScreen extends AbstractScreen {

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final Scanner scanner;

    public AssignTeacherScreen() {
        this.subjectService = SubjectService.getInstance();
        this.teacherService = TeacherService.getInstance();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void display() {
        clearScreen();
        System.out.println("┌─────────────────────────────┐");
        System.out.println("│  GÁN GIÁO VIÊN CHO MÔN HỌC │");
        System.out.println("└─────────────────────────────┘");

        // Hiển thị danh sách môn học
        subjectService.displayAllSubjects();

        // Hiển thị danh sách giáo viên
        teacherService.displayAllTeachers();

        System.out.println("Nhấn Enter để tiếp tục gán giáo viên...");
    }

    @Override
    public void handleInput() {
        // 1️⃣ Chọn môn học
        System.out.print("Nhập mã môn học muốn gán giáo viên: ");
        String subjectID = scanner.nextLine().trim();
        Optional<Subject> optSubject = subjectService.findById(subjectID);
        if (optSubject.isEmpty()) {
            System.out.println("Không tìm thấy môn học với ID: " + subjectID);
            return;
        }
        Subject subject = optSubject.get();
        System.out.println("Môn học: " + subject.getSubjectName());
        System.out.println("Giáo viên hiện tại: " + subject.getTeachersInCharge());

        // 2️⃣ Nhập giáo viên muốn gán (nhiều, cách nhau bởi dấu ,)
        System.out.print("Nhập mã giáo viên cần gán (cách nhau bằng dấu ,): ");
        String input = scanner.nextLine().trim();
        String[] teacherIDs = input.split("\\s*,\\s*");

        List<String> assignedTeachers = new ArrayList<>();
        for (String teacherID : teacherIDs) {
            Optional<Teacher> optTeacher = teacherService.findById(teacherID);
            if (optTeacher.isPresent()) {
                assignedTeachers.add(teacherID);
            } else {
                System.out.println("⚠ Không tìm thấy giáo viên với ID: " + teacherID);
            }
        }

        if (!assignedTeachers.isEmpty()) {
            subjectService.assignTeachersToSubject(subjectID, assignedTeachers);
            System.out.println("Danh sách giáo viên sau khi gán: " + subject.getTeachersInCharge());
        } else {
            System.out.println("Không có giáo viên hợp lệ để gán.");
        }
    }
}
