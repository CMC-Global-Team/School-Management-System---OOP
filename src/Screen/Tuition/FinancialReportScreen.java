package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;

import Utils.InputUtil;

import java.util.List;

/**
 * FinancialReportScreen - Màn hình báo cáo tài chính học phí

 */
public class FinancialReportScreen extends AbstractScreen {


    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         Báo cáo tài chính                │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        String filterYear = InputUtil.getString("Nhập năm học để lọc (VD: 2024-2025, Enter để bỏ qua): ");



        pause();
    }
}