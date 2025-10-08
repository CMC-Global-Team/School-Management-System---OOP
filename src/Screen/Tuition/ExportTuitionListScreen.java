package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;
import Services.TuitionService;
import Utils.FileUtil;
import Utils.InputUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ExportTuitionListScreen - Màn hình xuất danh sách học phí
 */
public class ExportTuitionListScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        XUẤT DANH SÁCH HỌC PHÍ            │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN XUẤT:");
        System.out.println("- File sẽ được lưu trong thư mục data/");
        System.out.println("- Tên file tự động với timestamp");
        System.out.println("- Có thể xuất theo nhiều tiêu chí khác nhau");
        System.out.println("- File xuất có định dạng CSV dễ đọc");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("XUẤT DANH SÁCH HỌC PHÍ");
        System.out.println("=".repeat(50));
        
        TuitionService service = TuitionService.getInstance();
        
        if (service.getTotalTuitions() == 0) {
            System.out.println("Không có dữ liệu học phí để xuất!");
            pause();
            return;
        }
        
        System.out.println("\nChọn loại xuất:");
        System.out.println("1. Xuất tất cả học phí");
        System.out.println("2. Xuất học phí đã thu");
        System.out.println("3. Xuất học phí chưa thu");
        System.out.println("4. Xuất theo năm học");
        System.out.println("5. Xuất theo học sinh");
        
        int choice = InputUtil.getInt("Lựa chọn (1-5): ");
        
        String fileName = generateFileName();
        List<Tuition> tuitionsToExport = null;
        String exportTitle = "";
        
        switch (choice) {
            case 1:
                tuitionsToExport = service.getAllTuitions();
                exportTitle = "Tất cả học phí";
                break;
            case 2:
                tuitionsToExport = service.searchTuitions("đã thu");
                exportTitle = "Học phí đã thu";
                break;
            case 3:
                tuitionsToExport = service.searchTuitions("chưa thu");
                exportTitle = "Học phí chưa thu";
                break;
            case 4:
                String schoolYear = InputUtil.getNonEmptyString("Nhập năm học (YYYY-YYYY): ");
                tuitionsToExport = service.searchTuitions(schoolYear);
                exportTitle = "Học phí năm học " + schoolYear;
                break;
            case 5:
                String studentId = InputUtil.getNonEmptyString("Nhập mã học sinh: ");
                tuitionsToExport = service.searchTuitions(studentId);
                exportTitle = "Học phí của học sinh " + studentId;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                pause();
                return;
        }
        
        if (tuitionsToExport == null || tuitionsToExport.isEmpty()) {
            System.out.println("Không có dữ liệu phù hợp để xuất!");
            pause();
            return;
        }
        
        try {
            List<String> lines = createExportContent(tuitionsToExport, exportTitle);
            FileUtil.writeLines(fileName, lines);
            System.out.println("\nXuất thành công!");
            System.out.println("File: " + fileName);
            System.out.println("Số lượng: " + tuitionsToExport.size() + " học phí");
        } catch (IOException e) {
            System.out.println("\nLỗi khi xuất file: " + e.getMessage());
        }
        
        pause();
    }
    
    private String generateFileName() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "data/tuition_export_" + timestamp + ".txt";
    }
    
    private List<String> createExportContent(List<Tuition> tuitions, String title) {
        List<String> lines = new java.util.ArrayList<>();
        
        lines.add("=".repeat(60));
        lines.add("                    BÁO CÁO HỌC PHÍ");
        lines.add("=".repeat(60));
        lines.add("Tiêu đề: " + title);
        lines.add("Ngày xuất: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        lines.add("Tổng số: " + tuitions.size() + " học phí");
        lines.add("");
        
        // Header
        lines.add("Mã HP,Mã HS,Học kỳ,Năm học,Số tiền,Ngày thu,Phương thức,Trạng thái,Ghi chú");
        
        // Data
        for (Tuition tuition : tuitions) {
            lines.add(String.format("%s,%s,%d,%s,%.0f,%s,%s,%s,%s",
                tuition.getTuitionId(),
                tuition.getStudentId(),
                tuition.getSemester(),
                tuition.getSchoolYear(),
                tuition.getAmount(),
                tuition.getPaymentDate(),
                tuition.getMethod() != null ? tuition.getMethod() : "",
                tuition.getStatus(),
                tuition.getNote() != null ? tuition.getNote() : ""
            ));
        }
        
        lines.add("");
        lines.add("=".repeat(60));
        lines.add("Kết thúc báo cáo");
        lines.add("=".repeat(60));
        
        return lines;
    }
}