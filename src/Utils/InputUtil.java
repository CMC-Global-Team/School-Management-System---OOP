package Utils;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputUtil {
    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static String getNonEmptyString(String prompt) {
        String input;
        do {
            input = getString(prompt);
            if (input.isEmpty()) {
                System.out.println("Vui lòng nhập giá trị hợp lệ!");
            }
        } while (input.isEmpty());
        return input;
    }

    public static String getEmail(String prompt) {
        String email;
        do {
            email = getString(prompt);
            Matcher matcher = EMAIL_PATTERN.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Email không hợp lệ! Vui lòng nhập lại.");
            } else {
                break;
            }
        } while (true);
        return email;
    }

    public static int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ!");
            }
        }
    }

    
}