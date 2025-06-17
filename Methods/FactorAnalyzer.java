import java.util.Scanner;

public class FactorAnalyzer {
    public static int[] findFactors(int num) {
        int count = 0;
        for (int i = 1; i <= num; i++) if (num % i == 0) count++;
        int[] factors = new int[count];
        for (int i = 1, j = 0; i <= num; i++) if (num % i == 0) factors[j++] = i;
        return factors;
    }

    public static int sumFactors(int[] factors) {
        int sum = 0;
        for (int factor : factors) sum += factor;
        return sum;
    }

    public static int sumSquares(int[] factors) {
        int sum = 0;
        for (int factor : factors) sum += Math.pow(factor, 2);
        return sum;
    }

    public static int productFactors(int[] factors) {
        int product = 1;
        for (int factor : factors) product *= factor;
        return product;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int num = sc.nextInt();
        int[] factors = findFactors(num);
        
        System.out.print("Factors: ");
        for (int factor : factors) System.out.print(factor + " ");
        
        System.out.println("\nSum: " + sumFactors(factors));
        System.out.println("Sum of squares: " + sumSquares(factors));
        System.out.println("Product: " + productFactors(factors));
    }
}