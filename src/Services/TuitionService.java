package Services;

import Models.Tuition;
import Utils.InputUtil;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class TuitionService {
    private static TuitionService instance;
    private final TuitionRepository repository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final StudentRepository studentRepository;

    private TuitionService() {
        this.repository = new TuitionRepository();

        this.studentRepository = new StudentRepository();
    }

    /**
     * Lấy instance duy nhất của TuitionService (Singleton)
     */
    public static TuitionService getInstance() {
        if (instance == null) {
            instance = new TuitionService();
        }
        return instance;
    }

    /**
     * Thêm học phí mới
     */
    public boolean addTuition(String tuitionId, String studentId, int semester, String schoolYear,
                              double amount, LocalDate paymentDate, String method, String status, String note) {

        java.util.Scanner sc = new java.util.Scanner(System.in);

        //  1. Kiểm tra mã học phí (định dạng TF + 4 số)
        while (tuitionId == null || !tuitionId.matches("^TF\\d{4}$") || repository.exists(tuitionId)) {
            if (tuitionId == null || tuitionId.trim().isEmpty()) {
                System.out.print(" Mã học phí không được để trống! → Nhập lại (định dạng TFxxxx): ");
            } else if (!tuitionId.matches("^TF\\d{4}$")) {
                System.out.print(" Mã học phí sai định dạng! (VD: TF0001) → Nhập lại: ");
            } else {
                System.out.print(" Mã học phí '" + tuitionId + "' đã tồn tại! → Nhập mã khác: ");
            }
            tuitionId = sc.nextLine().trim();
        }

        //  2. Kiểm tra mã học sinh
        while (studentId == null || studentId.trim().isEmpty() || !studentRepository.exists(studentId)) {
            if (studentId == null || studentId.trim().isEmpty()) {
                System.out.print(" Mã học sinh không được để trống! → Nhập lại: ");
            } else {
                System.out.print(" Không tìm thấy học sinh có mã '" + studentId + "'! → Nhập lại: ");
            }
            studentId = sc.nextLine().trim();
        }

        //  3. Kiểm tra học sinh đã có học phí chưa
        List<Tuition> existingTuitions = repository.findAll();
        String finalStudentId = studentId;
        boolean hasTuition = existingTuitions.stream()
                .anyMatch(t -> t.getStudentId().equalsIgnoreCase(finalStudentId));

        if (hasTuition) {
            System.out.println("  ️ Học sinh có mã '" + studentId + "' đã có học phí trong hệ thống!");
            return false;
        }

        //  4. Nhập năm học đúng định dạng YYYY-YYYY và năm đầu <= năm cuối
        while (true) {
            if (schoolYear != null && schoolYear.matches("^\\d{4}-\\d{4}$")) {
                String[] parts = schoolYear.split("-");
                int startYear = Integer.parseInt(parts[0]);
                int endYear = Integer.parseInt(parts[1]);
                if (startYear <= endYear) {
                    break; // hợp lệ
                } else {
                    System.out.print(" Năm học không hợp lệ! Năm bắt đầu phải <= năm kết thúc → Nhập lại: ");
                }
            } else {
                System.out.print(" Năm học sai định dạng! (VD: 2023-2024) → Nhập lại: ");
            }
            schoolYear = sc.nextLine().trim();
        }


        //  5. Số tiền phải là số dương, và tự cộng thêm 3 số 0
        amount = -1;
        DecimalFormat df = new DecimalFormat("#,###");

        while (amount <= 0) {
            System.out.print("Nhập số tiền (đơn vị: nghìn đồng, vd: 5): ");
            try {
                amount = Double.parseDouble(sc.nextLine().trim());
                if (amount <= 0) {
                    System.out.println(" Số tiền phải lớn hơn 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Vui lòng nhập số hợp lệ!");
                amount = -1;
            }
        }

// Nhân thêm 1000 để ra số thực tế
        amount *= 1000;

//  Hiển thị ra với dấu chấm phân cách hàng nghìn
        System.out.println("→ Số tiền thực thu: " + df.format(amount));

        //  6. Kiểm tra ngày thu không được lớn hơn hôm nay
        while (paymentDate == null || paymentDate.isAfter(LocalDate.now())) {
            try {
                System.out.print("Nhập ngày thu (dd/MM/yyyy): ");
                String input = sc.nextLine().trim();
                paymentDate = LocalDate.parse(input, DATE_FORMATTER);

                if (paymentDate.isAfter(LocalDate.now())) {
                    System.out.println(" Ngày thu không được vượt quá hôm nay (" + LocalDate.now().format(DATE_FORMATTER) + ")");
                    paymentDate = null; // reset để nhập lại
                }
            } catch (DateTimeParseException e) {
                System.out.println(" Định dạng ngày không hợp lệ, phải là dd/MM/yyyy. Nhập lại!");
                paymentDate = null;
            }
        }

        //  7. Chọn trạng thái học phí bằng menu số
        int statusCode = -1;
        while (statusCode < 0 || statusCode > 1) {
            System.out.println("\nChọn trạng thái học phí:");
            System.out.println("0 - Chưa thu");
            System.out.println("1 - Đã thu");
            statusCode = InputUtil.getInt("Nhập lựa chọn (0/1): ");

            switch (statusCode) {
                case 0 -> status = "CHƯA THU";
                case 1 -> status = "ĐÃ THU";
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }

//  8. Chọn phương thức thanh toán (chỉ khi đã thu)
        if (status.equals("ĐÃ THU")) {
            int methodCode = -1;
            while (methodCode < 0 || methodCode > 1) {
                System.out.println("\nChọn phương thức thanh toán:");
                System.out.println("0 - Tiền mặt");
                System.out.println("1 - Chuyển khoản");
                methodCode = InputUtil.getInt("Nhập lựa chọn (0/1): ");

                switch (methodCode) {
                    case 0 -> method = "TIỀN MẶT";
                    case 1 -> method = "CHUYỂN KHOẢN";
                    default -> System.out.println("Lựa chọn không hợp lệ!");
                }
            }
        } else {
            method = ""; // nếu chưa thu thì bỏ trống phương thức
        }


        //  9. Lưu học phí
        Tuition tuition = new Tuition(tuitionId, studentId, semester, schoolYear, amount, paymentDate, method, status, note);
        if (repository.add(tuition)) {
            System.out.println(" Thêm học phí thành công cho học sinh " + studentId);
            return true;
        } else {
            System.out.println(" Lỗi: Không thể thêm học phí!");
            return false;
        }
    }

    /**
     * Cập nhật học phí từ các input (có thể bỏ trống nếu không muốn thay đổi)
     */
    public boolean updateTuition(Tuition t,
                                 String studentIdInput,
                                 String semesterInput,
                                 String schoolYearInput,
                                 String amountInput,
                                 String paymentDateInput,
                                 String statusInput,
                                 String methodInput,
                                 String noteInput) {
        if (t == null) {
            System.out.println("Lỗi: Tuition không được null!");
            return false;
        }

        if (!repository.exists(t.getTuitionId())) {
            System.out.println("Lỗi: Không tìm thấy học phí với mã '" + t.getTuitionId() + "'!");
            return false;
        }

        // StudentId
        if (studentIdInput != null && !studentIdInput.isEmpty()) {
            t.setStudentId(studentIdInput);
        }

        // Semester
        if (semesterInput != null && !semesterInput.isEmpty()) {
            try {
                t.setSemester(Integer.parseInt(semesterInput));
            } catch (NumberFormatException ignored) {
            }
        }

        // SchoolYear

        if (schoolYearInput != null && !schoolYearInput.isEmpty()) {
            if (schoolYearInput.matches("^\\d{4}-\\d{4}$")) {
                String[] parts = schoolYearInput.split("-");
                int startYear = Integer.parseInt(parts[0]);
                int endYear = Integer.parseInt(parts[1]);
                if (startYear <= endYear) {
                    t.setSchoolYear(schoolYearInput);
                } else {
                    System.out.println(" Năm học không hợp lệ, giữ nguyên giá trị cũ.");
                }
            } else {
                System.out.println(" Năm học sai định dạng, giữ nguyên giá trị cũ.");
            }
        }

        if (schoolYearInput != null && !schoolYearInput.isEmpty()) {
            if (schoolYearInput.matches("^\\d{4}-\\d{4}$")) {
                t.setSchoolYear(schoolYearInput);
            }
        }

        // Amount
        if (amountInput != null && !amountInput.isEmpty()) {
            try {
                double money = Double.parseDouble(amountInput);
                if (money >= 0) t.setAmount(money);
            } catch (NumberFormatException ignored) {
            }
        }

        // PaymentDate
        if (paymentDateInput != null && !paymentDateInput.isEmpty()) {
            try {
                LocalDate newDate = LocalDate.parse(paymentDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (!newDate.isAfter(LocalDate.now())) t.setPaymentDate(newDate);
            } catch (Exception ignored) {
            }
        }

        // Status

        if (statusInput != null && !statusInput.isEmpty()) {
            int statusCode = -1;
            if (statusInput.matches("[01]")) {
                statusCode = Integer.parseInt(statusInput);
            }
            if (statusCode == 0) t.setStatus("CHƯA THU");
            else if (statusCode == 1) t.setStatus("ĐÃ THU");

            // Method
            if ("ĐÃ THU".equals(t.getStatus())) {
                int methodCode = -1;
                if (methodInput != null && methodInput.matches("[01]")) {
                    methodCode = Integer.parseInt(methodInput);
                }
                if (methodCode == 0) t.setMethod("TIỀN MẶT");
                else if (methodCode == 1) t.setMethod("CHUYỂN KHOẢN");
            } else {
                t.setMethod(null);
            }
        }


        // Note
        if (noteInput != null && !noteInput.isEmpty()) {
            t.setNote(noteInput);
        }

        // Cập nhật vào repository
        if (repository.update(t)) {
            System.out.println(" Cập nhật học phí thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể cập nhật học phí!");
            return false;
        }
    }

    /**
     * Xóa học phí theo ID
     */
    public boolean deleteTuition(String tuitionId) {
        if (tuitionId == null || tuitionId.trim().isEmpty()) {
            System.out.println("Lỗi: Mã học phí không được để trống!");
            return false;
        }

        if (!repository.exists(tuitionId)) {
            System.out.println("Lỗi: Không tìm thấy học phí với mã '" + tuitionId + "'!");
            return false;
        }

        if (repository.delete(tuitionId)) {
            System.out.println(" Xóa học phí thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể xóa học phí!");
            return false;
        }
    }

    /**
     * Tìm học phí theo ID
     */
    public Optional<Tuition> findById(String tuitionId) {
        return repository.findById(tuitionId);
    }

    /**
     * Lấy tất cả học phí
     */
    public List<Tuition> getAllTuitions() {
        return repository.findAll();
    }

    public List<Tuition> searchTuitions(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // Trả về toàn bộ danh sách
            return getAllTuitions();
        }

        String key = keyword.trim().toLowerCase();

        return repository.findAll().stream()
                .filter(t -> t.getTuitionId().equalsIgnoreCase(key) ||
                        t.getStudentId().equalsIgnoreCase(key))
                .toList();
    }

    /**
     * Kiểm tra mã học phí đã tồn tại chưa
     */
    public boolean isTuitionIdExists(String tuitionId) {
        return repository.exists(tuitionId);
    }

    /**
     * Đếm tổng số học phí
     */
    public int getTotalTuitions() {
        return repository.count();
    }

    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }

    /**
     * Hiển thị kết quả tìm kiếm học phí
     */
    public void displaySearchResults(String keyword) {
        List<Tuition> results = searchTuitions(keyword);

        if (results.isEmpty()) {
            System.out.println("\nKhông tìm thấy học phí nào phù hợp với từ khóa: '" + keyword + "'");
            return;
        }
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                     KẾT QUẢ TÌM KIẾM HỌC PHÍ                                                  │");
        System.out.println("├───────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-12s %-12s %-8s %-10s %-10s %-12s %-12s %-10s %-20s │%n",
                "Mã HP", "Mã SV", "Học kỳ", "Năm học", "Số tiền", "Ngày TT", "Phương thức", "Trạng thái", "Ghi chú");
        System.out.println("├───────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");

        for (Tuition t : results) {
            System.out.printf("│ %-12s %-12s %-8d %-10s %-10.2f %-12s %-12s %-10s %-20s │%n",
                    truncate(t.getTuitionId(), 12),
                    truncate(t.getStudentId(), 12),
                    t.getSemester(),
                    truncate(t.getSchoolYear(), 10),
                    t.getAmount(),
                    truncate(t.getPaymentDate() != null ? t.getPaymentDate().toString() : "", 12),
                    truncate(t.getMethod(), 12),
                    truncate(t.getStatus(), 10),
                    truncate(t.getNote(), 20)
            );
        }

        System.out.println("└───────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tìm thấy: " + results.size() + " học phí");
    }
}
