import java.util.*;
public class TotalIncomeCalculator{
public static void main(String[] args){
Scanner input=new Scanner(System.in);
System.out.print("Enter salary: ");
double salary=input.nextDouble();
System.out.print("Enter bonus: ");
double bonus=input.nextDouble();
double total=salary+bonus;
System.out.println("The salary is INR "+salary+" and the bonus is INR "+bonus+". Hence Total Income is INR "+total);
}
}
