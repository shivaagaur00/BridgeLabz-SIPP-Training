import java.util.*;
class QuotientRemainder{
public static void main(String[] args){
Scanner input=new Scanner(System.in);
System.out.print("Enter number1: ");
int number1=input.nextInt();
System.out.print("Enter number2: ");
int number2=input.nextInt();
int quotient=number1/number2;
int remainder=number1%number2;
System.out.println("The Quotient is "+quotient+" and Reminder is "+remainder+" of two number "+number1+" and "+number2);
}
}
