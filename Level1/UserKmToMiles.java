import java.util.*;
class UserKmToMiles {
public static void main(String[] args) {
Scanner sc = new Scanner(System.in);
double km;
System.out.print("Enter distance in km: ");
km = sc.nextDouble();
double miles = km / 1.6;
        System.out.println("The total miles is " + miles + " mile for the given " + km + " km");
    }
}