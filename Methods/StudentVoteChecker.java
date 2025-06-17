import java.util.Scanner;

public class StudentVoteChecker {
    public static boolean canStudentVote(int age) {
        return age >= 18;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] ages = new int[10];
        for (int i = 0; i < 10; i++) {
            System.out.print("Enter age for student " + (i+1) + ": ");
            ages[i] = sc.nextInt();
            System.out.println(canStudentVote(ages[i]) ? "Can vote" : "Cannot vote");
        }
    }
}