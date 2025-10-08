package Screen.Subject;


import Screen.AbstractScreen;
import Services.SubjectService;
import Models.Subject;
import java.util.*;

import java.util.Optional;


public class UpdateSubjectScreen extends AbstractScreen {
    private final SubjectService subjectService;


    public UpdateSubjectScreen() {
        this.subjectService = SubjectService.getInstance();
    }


    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           CẬP NHẬT MÔN HỌC               │");
        System.out.println("└──────────────────────────────────────────┘");
    }


    @Override
    public void handleInput() {
        String id = input("Nhập mã môn học cần cập nhật: ");
        Optional<Subject> opt = subjectService.findById(id);


        if (opt.isEmpty()) {
            System.out.println("Không tìm thấy môn học với mã: " + id);
            pause();
            return;
        }


        Subject s = opt.get();
        System.out.println("Thông tin hiện tại: " + s);


        String name = input("Tên mới (" + s.getSubjectName() + "): ");
        if (!name.isEmpty()) s.setSubjectName(name);


        String lessonStr = input("Số tiết (" + s.getLessonCount() + "): ");
        if (!lessonStr.isEmpty()) s.setLessonCount(Integer.parseInt(lessonStr));


        String coefStr = input("Hệ số (" + s.getConfficient() + "): ");
        if (!coefStr.isEmpty()) s.setConfficient(Double.parseDouble(coefStr));


        String type = input("Loại môn (" + s.getSubjectType() + "): ");
        if (!type.isEmpty()) s.setSubjectType(type);


        String desc = input("Mô tả (" + s.getDescription() + "): ");
        if (!desc.isEmpty()) s.setDescription(desc);


        System.out.println("Giáo viên phụ trách hiện tại: " + String.join(", ", s.getTeachersInCharge()));
        String teacherInput = input("Nhập danh sách giáo viên mới (cách nhau bằng dấu ,) hoặc Enter để giữ nguyên: ");
        if (!teacherInput.isEmpty()) {
            String[] teacherIDs = teacherInput.split("\\s*,\\s*");
            s.setTeachersInCharge(Arrays.asList(teacherIDs));
        }

        String status = input("Trạng thái (" + s.getStatus() + "): ");
        if (!status.isEmpty()) s.setStatus(status);


        if (subjectService.updateSubject(s)) {
            System.out.println("Cập nhật thành công!");
        }


        pause();
    }
}
