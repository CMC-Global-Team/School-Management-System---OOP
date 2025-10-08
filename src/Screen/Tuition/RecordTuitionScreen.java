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

    }
}

