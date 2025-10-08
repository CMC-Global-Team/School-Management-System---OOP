package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;

import Utils.InputUtil;


/**
 * ExportTuitionListScreen - Màn hình xuất danh sách học phí

 */
public class ExportTuitionListScreen extends AbstractScreen {


    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        Xuất Danh Sách Học Phí            │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n[Thông báo] Xuất danh sách học phí...");

        String outputFile = "data/tuition_export.txt";


        InputUtil.pressEnterToContinue();
    }
}