import java.util.Scanner;

public class WindChill {
    public static double calculateWindChill(double temperature, double windSpeed) {
        return 35.74 + 0.6215 * temperature - 35.75 * Math.pow(windSpeed, 0.16) + 0.4275 * temperature * Math.pow(windSpeed, 0.16);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter temperature (F): ");
        double temp = sc.nextDouble();
        System.out.print("Enter wind speed (mph): ");
        double wind = sc.nextDouble();
        System.out.println("Wind chill: " + calculateWindChill(temp, wind));
    }
}