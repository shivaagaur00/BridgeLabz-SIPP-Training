import java.util.Scanner;

public class MaxOfThree {
    public static void main(String[] args) {
        int a = input("first"), b = input("second"), c = input("third");
        System.out.println("Maximum: " + findMax(a, b, c));
    }

    static int input(String label) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter " + label + " number: ");
        return sc.nextInt();
    }

    static int findMax(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }
}
