package Screen.Grade;

import Screen.AbstractScreen;

public class GradeClassificationScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Xếp loại học lực               │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n[Thong bao] Chuc nang xep loai hoc luc cho Hoc Sinh dang duoc phat trien...");
        System.out.println("Cac thong tin se bao gom:");
        System.out.println("- Ma hoc sinh");
        System.out.println("- Nhap diem trung binh");
        System.out.println("- Hoc ki");
        System.out.println("- Nam hoc");
        pause();
    }
}