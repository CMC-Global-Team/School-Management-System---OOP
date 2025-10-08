package Screen.Subject;

import Screen.AbstractScreen;
import Services.SubjectService;
import Models.Subject;
import Utils.InputUtil;

import java.util.Arrays;
import java.util.List;

public class AddSubjectScreen extends AbstractScreen {
    private final SubjectService subjectService;

    public AddSubjectScreen() {
        this.subjectService = SubjectService.getInstance();
    }

    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           THÊM MÔN HỌC MỚI               │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nNhập thông tin môn học:");
        String id;
        while(true){
            id = InputUtil.getNonEmptyString("Mã môn học: ");
            if(SubjectService.getInstance().isSubjectIdExists(id)){
                System.out.println("Mã môn học " + id + " đã tồn tại!");
            }
            else{
                break;
            }
        }
        String name;
        while (true) {
            name = InputUtil.getNonEmptyString("Nhập tên môn học: ");
            if (SubjectService.getInstance().isSubjectNameExists(name)) {
                System.out.println("Tên môn học \"" + name + "\" đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                break;
            }
        }

        int lessonCount = InputUtil.getInt("Số tiết học: ");
        double confficient = InputUtil.getDouble("Hệ số: ");

        int typeChoice;
        String type = "";
        while (true) {
            System.out.println("Loại Môn: 0 - Bắt buộc, 1 - Tự chọn");
            typeChoice = InputUtil.getInt("Chọn loại (0 hoặc 1): ");
            if(typeChoice == 0) {
                type = "Bắt buộc";
                break;
            } else if(typeChoice == 1) {
                type = "Tự chọn";
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập 0 hoặc 1.");
            }
        }
        String description = input("Mô tả: ");
        String teacherInput = input("Giáo viên phụ trách (cách nhau bởi dấu ','): ");
        List<String> teachersList = Arrays.asList(teacherInput.split(","));

        int statusChoice;
        String status = "";
        while (true) {
            System.out.println("Trạng thái: 0 - Đang dạy, 1 - Ngừng");
            statusChoice = InputUtil.getInt("Chọn trạng thái (0 hoặc 1): ");
            if(statusChoice == 0) {
                status = "Đang dạy";
                break;
            } else if(statusChoice == 1) {
                status = "Ngừng";
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập 0 hoặc 1.");
            }
        }

        if (subjectService.addSubject(id, name, lessonCount, confficient, type, description, teachersList, status)) {
            System.out.println("\nThông tin môn học đã thêm:");
            subjectService.findById(id).ifPresent(System.out::println);
        }

        pause();

    }
}
