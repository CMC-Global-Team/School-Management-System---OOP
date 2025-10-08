package Screen.Grade;

import Screen.AbstractScreen;

public class ReportScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Bao Cao Diem                   │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n[Thong bao] Chuc nang bao cao diem lop dang duoc phat trien...");
        System.out.println("Chon kieu bao cao");
        System.out.println("- Tao bao cao theo mon hoc");
        System.out.println("- Tao bao cao theo hoc ki");
        System.out.println("- Tao bao cao theo nam hoc");
        System.out.println("- Tao bao cao theo lop");


        pause();
    }
}