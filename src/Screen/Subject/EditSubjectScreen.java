package Screen.Subject;


import Models.Subject;
import Screen.AbstractScreen;
import Services.SubjectService;
import Utils.InputUtil;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Optional;


public class EditSubjectScreen extends AbstractScreen {


    private final SubjectService subjectService;


    public EditSubjectScreen() {
        this.subjectService = SubjectService.getInstance();
    }


    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│          CHỈNH SỬA THÔNG TIN MÔN HỌC        │");
        System.out.println("└──────────────────────────────────────────────┘");
    }


    @Override
    public void handleInput() {
        // Nhập mã môn học cần chỉnh sửa
        String subjectId = InputUtil.getNonEmptyString("Nhập mã môn học cần chỉnh sửa: ");
        Optional<Subject> optionalSubject = subjectService.findById(subjectId);


        if (optionalSubject.isEmpty()) {
            System.out.println("Không tìm thấy môn học với mã '" + subjectId + "'!");
            InputUtil.pressEnterToContinue();
            return;
        }


        Subject s = optionalSubject.get();
        System.out.println("\n🔎 Thông tin hiện tại:");
        System.out.println("Tên môn học: " + s.getSubjectName());
        System.out.println("Số tiết: " + s.getLessonCount());
        System.out.println("Hệ số: " + s.getConfficient());
        System.out.println("Loại môn: " + s.getSubjectType());
        System.out.println("Mô tả: " + s.getDescription());
        System.out.println("Giáo viên phụ trách: " + String.join(", ", s.getTeachersInCharge()));
        System.out.println("Trạng thái: " + s.getStatus());


        System.out.println("\n--- Nhập thông tin mới (bỏ trống nếu không thay đổi) ---");


        // Cập nhật từng trường
        String nameInput = InputUtil.getString("Tên môn học mới: ");
        if (!nameInput.isEmpty()) s.setSubjectName(nameInput);


        String lessonsInput = InputUtil.getString("Số tiết mới: ");
        if (!lessonsInput.isEmpty()) {
            try {
                s.setLessonCount(Integer.parseInt(lessonsInput));
            } catch (NumberFormatException e) {
                System.out.println("Dữ liệu không hợp lệ, giữ nguyên giá trị cũ.");
            }
        }


        String coeffInput = InputUtil.getString("Hệ số mới: ");
        if (!coeffInput.isEmpty()) {
            try {
                s.setConfficient(Double.parseDouble(coeffInput));
            } catch (NumberFormatException e) {
                System.out.println("Dữ liệu không hợp lệ, giữ nguyên giá trị cũ.");
            }
        }



        while (true) {
            String typeInput = InputUtil.getString("Loại môn mới (0 - Bắt buộc, 1 - Tự chọn, Enter để giữ nguyên): ");
            if (typeInput.isEmpty()) break; // không thay đổi
            if (typeInput.equals("0")) {
                s.setSubjectType("Bắt buộc");
                break;
            } else if (typeInput.equals("1")) {
                s.setSubjectType("Tự chọn");
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ! Nhập 0 hoặc 1 hoặc Enter để bỏ qua.");
            }
        }


        String descInput = InputUtil.getString("Mô tả mới: ");
        if (!descInput.isEmpty()) s.setDescription(descInput);

        String teacherInput = InputUtil.getString("Giáo viên phụ trách mới (ngăn cách bằng dấu , nếu nhiều): ");
        if (!teacherInput.isEmpty()) {
            List<String> teachers = Arrays.stream(teacherInput.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            s.setTeachersInCharge(new ArrayList<>(teachers)); // đảm bảo mutable list
        }




        while (true) {
            String statusInput = InputUtil.getString("Trạng thái mới (0 - Đang dạy, 1 - Ngừng, Enter để giữ nguyên): ");
            if (statusInput.isEmpty()) break; // không thay đổi
            if (statusInput.equals("0")) {
                s.setStatus("Đang dạy");
                break;
            } else if (statusInput.equals("1")) {
                s.setStatus("Ngừng");
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ! Nhập 0 hoặc 1 hoặc Enter để bỏ qua.");
            }
        }


        // Cập nhật qua service
        if (subjectService.updateSubject(s)) {
            System.out.println("Đã cập nhật thông tin môn học thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }


        InputUtil.pressEnterToContinue();
    }
}
