package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;
import Utils.InputUtil;

import java.util.Optional;

/**
 * TuitionDiscountScreen - Màn hình miễn giảm học phí
 *
 */
public class TuitionDiscountScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         Miễn Giảm Học Phí                │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        String tuitionId = InputUtil.getNonEmptyString("Nhập mã học phí cần miễn giảm: ");




        InputUtil.pressEnterToContinue();
    }
}
