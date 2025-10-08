package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;

import Utils.InputUtil;


/**
 * SearchForTuition - Màn hình tra cứu thông tin học phí

 */
public class SearchForTuition extends AbstractScreen {




    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         Tra Cứu Thông Tin Học Phí        │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override

    public void handleInput() {
        System.out.println("Nhập mã học phí hoặc mã học sinh (Nhấn Enter để xem tất cả): ");
        String keyword = InputUtil.getString("").trim().toLowerCase(); // cho phép để trống

    }
}
