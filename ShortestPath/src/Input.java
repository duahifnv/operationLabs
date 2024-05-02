import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
public class Input {
    public static int Number(String comm, int min, int max) {
        Scanner scan = new Scanner(System.in);
        int input;
        while(true) {
            System.out.print(comm);
            try {
                input = scan.nextInt();
            } catch (Exception NumberFormatException) {
                System.out.println("Введенный символ не является числом");
                scan.nextLine();
                continue;
            }
            if (input < min || input > max) {
                System.out.println("Введенное число вне диапазона");
                continue;
            }
            break;
        }
        return input;
    }
}
