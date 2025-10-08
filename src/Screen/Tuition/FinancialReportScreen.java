package Screen.Tuition;

import Models.TuitionReport;
import Screen.AbstractScreen;
import Services.TuitionService;
import Utils.InputUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * FinancialReportScreen - Màn hình báo cáo tài chính học phí
 */
public class FinancialReportScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         BÁO CÁO TÀI CHÍNH                 │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Năm học lọc: YYYY-YYYY (VD: 2024-2025) - Để trống để xem tất cả        │");
        System.out.println("│                                                                         │");
        System.out.println("│ NỘI DUNG BÁO CÁO:                                                        │");
        System.out.println("│ - Tổng quan tài chính (doanh thu dự kiến, thực tế, miễn giảm)          │");
        System.out.println("│ - Thống kê tỷ lệ (thu được, miễn giảm, chưa thu)                        │");
        System.out.println("│ - Phân tích chi tiết từng loại học phí                                  │");
        System.out.println("│ - Đánh giá tình hình thu học phí                                         │");
        System.out.println("│                                                                         │");
        System.out.println("│ LƯU Ý: Báo cáo được tính toán dựa trên dữ liệu hiện tại                 │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("BÁO CÁO TÀI CHÍNH HỌC PHÍ");
        System.out.println("=".repeat(50));
        
        TuitionService service = TuitionService.getInstance();
        
        // Kiểm tra có dữ liệu không
        if (service.getTotalTuitions() == 0) {
            System.out.println("Không có dữ liệu học phí để tạo báo cáo!");
            pause();
            return;
        }
        
        // Nhập năm học để lọc
        String filterYear = InputUtil.getString("Nhập năm học để lọc (VD: 2024-2025, Enter để bỏ qua): ");
        
        // Tạo báo cáo
        TuitionReport report = service.generateFinancialReport(filterYear);
        
        // Hiển thị báo cáo
        displayFinancialReport(report, filterYear);
        
        pause();
    }
    
    private void displayFinancialReport(TuitionReport report, String filterYear) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                           BÁO CÁO TÀI CHÍNH HỌC PHÍ");
        System.out.println("=".repeat(80));
        
        // Thông tin báo cáo
        System.out.println("Ngày tạo báo cáo: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        if (filterYear != null && !filterYear.trim().isEmpty()) {
            System.out.println("Năm học: " + filterYear);
        } else {
            System.out.println("Năm học: Tất cả");
        }
        
        System.out.println("\n" + "-".repeat(80));
        System.out.println("                           TỔNG QUAN TÀI CHÍNH");
        System.out.println("-".repeat(80));
        
        System.out.printf("│ %-30s │ %-20s │%n", "CHỈ SỐ", "GIÁ TRỊ");
        System.out.println("-".repeat(80));
        System.out.printf("│ %-30s │ %-20s │%n", "Tổng doanh thu dự kiến", String.format("%,.0f VND", report.expectedRevenue));
        System.out.printf("│ %-30s │ %-20s │%n", "Doanh thu thực tế", String.format("%,.0f VND", report.actualRevenue));
        System.out.printf("│ %-30s │ %-20s │%n", "Tổng miễn giảm", String.format("%,.0f VND", report.totalDiscount));
        System.out.printf("│ %-30s │ %-20s │%n", "Số học phí đã thu", String.format("%d học phí", report.countPaid));
        System.out.printf("│ %-30s │ %-20s │%n", "Số học phí chưa thu", String.format("%d học phí", report.countUnpaid));
        System.out.printf("│ %-30s │ %-20s │%n", "Tổng số học phí", String.format("%d học phí", report.countPaid + report.countUnpaid));
        
        System.out.println("-".repeat(80));
        
        // Tính tỷ lệ
        double paidPercentage = report.expectedRevenue > 0 ? (report.actualRevenue / report.expectedRevenue) * 100 : 0;
        double discountPercentage = report.expectedRevenue > 0 ? (report.totalDiscount / report.expectedRevenue) * 100 : 0;
        
        System.out.println("\n" + "-".repeat(80));
        System.out.println("                           THỐNG KÊ TỶ LỆ");
        System.out.println("-".repeat(80));
        
        System.out.printf("│ %-30s │ %-20s │%n", "Tỷ lệ thu được", String.format("%.1f%%", paidPercentage));
        System.out.printf("│ %-30s │ %-20s │%n", "Tỷ lệ miễn giảm", String.format("%.1f%%", discountPercentage));
        System.out.printf("│ %-30s │ %-20s │%n", "Tỷ lệ chưa thu", String.format("%.1f%%", 100 - paidPercentage));
        
        System.out.println("-".repeat(80));
        
        // Phân tích chi tiết
        System.out.println("\n" + "-".repeat(80));
        System.out.println("                           PHÂN TÍCH CHI TIẾT");
        System.out.println("-".repeat(80));
        
        if (report.countPaid > 0) {
            System.out.printf("Đã thu: %d học phí với tổng số tiền %,.0f VND%n", 
                report.countPaid, report.totalPaid);
        }
        
        if (report.countUnpaid > 0) {
            System.out.printf("Chưa thu: %d học phí với tổng số tiền %,.0f VND%n", 
                report.countUnpaid, report.totalUnpaid);
        }
        
        if (report.totalDiscount > 0) {
            System.out.printf("Miễn giảm: Tổng cộng %,.0f VND%n", report.totalDiscount);
        }
        
        // Đánh giá tình hình
        System.out.println("\n" + "-".repeat(80));
        System.out.println("                           ĐÁNH GIÁ TÌNH HÌNH");
        System.out.println("-".repeat(80));
        
        if (paidPercentage >= 90) {
            System.out.println("Tình hình thu học phí: XUẤT SẮC");
        } else if (paidPercentage >= 80) {
            System.out.println("Tình hình thu học phí: TỐT");
        } else if (paidPercentage >= 70) {
            System.out.println("Tình hình thu học phí: TRUNG BÌNH");
        } else {
            System.out.println("Tình hình thu học phí: CẦN CẢI THIỆN");
        }
        
        if (discountPercentage > 20) {
            System.out.println("Lưu ý: Tỷ lệ miễn giảm cao (" + String.format("%.1f%%", discountPercentage) + ")");
        }
        
        System.out.println("=".repeat(80));
    }
}