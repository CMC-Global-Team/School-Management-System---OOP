package Screen.Tuition;

import Screen.AbstractScreen;

import Utils.InputUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordTuitionScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│       Ghi nhận thanh toán học phí        │");
        System.out.println("└──────────────────────────────────────────┘");
    }
    @Override
    public void handleInput() {
        String tuitionId = InputUtil.getNonEmptyString("Nhập mã học phí: ");
        String studentId = InputUtil.getNonEmptyString("Mã học sinh: ");
        int semester = InputUtil.getInt("Học kỳ: ");
        String schoolYear = InputUtil.getNonEmptyString("Năm học: ");
        double amount = InputUtil.getDouble("Số tiền thu: ");
        String status = InputUtil.getNonEmptyString("Trạng thái: ");
        String method = InputUtil.getNonEmptyString("Phương thức thanh toán: ");
        String note = InputUtil.getString("Ghi chú (nếu có): ");
    }
}

