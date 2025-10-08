package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;

import Utils.InputUtil;

import java.util.Optional;

/**
 * DeleteTuitionScreen - Màn hình xóa thông tin học phí

 */
public class DeleteTuitionScreen extends AbstractScreen {


    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         Xoá Thông Tin Học Phí            │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {

    }
}
