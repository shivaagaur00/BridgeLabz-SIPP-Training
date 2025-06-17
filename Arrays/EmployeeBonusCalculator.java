import java.util.Scanner;

public class EmployeeBonusCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double[] salaries = new double[10];
        int[] years = new int[10];
        double[] bonuses = new double[10];
        double[] newSalaries = new double[10];
        double totalBonus = 0;
        double totalOldSalary = 0;
        double totalNewSalary = 0;
        
        for (int i = 0; i < 10; i++) {
            System.out.print("Enter salary for employee " + (i+1) + ": ");
            salaries[i] = sc.nextDouble();
            System.out.print("Enter years of service for employee " + (i+1) + ": ");
            years[i] = sc.nextInt();
            
            if (salaries[i] <= 0 || years[i] < 0) {
                System.out.println("Invalid input. Please enter again.");
                i--;
                continue;
            }
            
            totalOldSalary += salaries[i];
        }
        
        for (int i = 0; i < 10; i++) {
            if (years[i] > 5) {
                bonuses[i] = salaries[i] * 0.05;
            } else {
                bonuses[i] = salaries[i] * 0.02;
            }
            newSalaries[i] = salaries[i] + bonuses[i];
            totalBonus += bonuses[i];
            totalNewSalary += newSalaries[i];
        }
        
        System.out.println("Total bonus payout: " + totalBonus);
        System.out.println("Total old salary: " + totalOldSalary);
        System.out.println("Total new salary: " + totalNewSalary);
    }
}