import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
  public static double finalAmount;
  public static double startingVar;
    public static int tradingVar;
    public static boolean contribution;
    private static String add;
    private static int addingFrequency;
    private static double addValue;
    private static final DecimalFormat numberFormat = new DecimalFormat("#,###.00");
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        algorithm(userStartingValue(), userCompound(), tradingDays());
      finalAmount();
    }

    public static void algorithm(double startingValue, double compoundDaily, int tradingDay) {
        boolean contribution = contributionChecking();
        if(contribution) //if the user said yes or no to having a contribution
        {
            addingFrequency = userAdd(); //how much are they depositing every (their frequency)
        }
        boolean checkForContribution;
        finalAmount = startingValue;
        double yesterdayAmount;
        for (int i = 1; i < tradingDay + 1; i++) { //There is an equation for this A = P(1+r)^t, but this allows for me to better visualize/edit
            System.out.println("Day " + i); //What day it is
            checkForContribution = false;
            if (contribution) //Checking for contribution (deposit) *This entire function is at top cause it mimics depositing at beginning of day
            {
                if (i != 1 && i % addingFrequency == 0 || addingFrequency == 1) //Checking to make sure it isn't the first day (for week, bi-weekly etc. because it will be true. Daily just always it true)
                {
                    System.out.println("Deposited $" + numberFormat.format(addValue));
                    startingValue = startingValue + addValue;
                    checkForContribution = true;
                }
            }
            yesterdayAmount = finalAmount;
            finalAmount = startingValue * compoundDaily;
            startingValue = finalAmount;
            if (checkForContribution) //Running into error of having the depositing amount adding to the money goal, quick but meh efficient fix
            {
                System.out.println("Money Goal: $" + numberFormat.format(finalAmount - yesterdayAmount - addValue));
            } else {
                System.out.println("Money Goal: $" + numberFormat.format(finalAmount - yesterdayAmount));
            }
            System.out.println("End of Day Amount: $" + numberFormat.format(finalAmount));
            System.out.println();
        }
    }
public static boolean contributionChecking() //previously had it in the algorithm, but just wanted to make it cleaner. Adds more lines though
        //It checks whether or not the user wants to deposit money
    {
        boolean checkContribution = true;
        contribution = false;
        System.out.println("Do you want to add money? y/n: ");
        scan.nextLine();
        while(checkContribution) {
            String check = scan.nextLine();
            if (check.equals("y") || check.equals("n")) {
                checkContribution = false;
                if (check.equals("y")) {
                    contribution = true;
                }
            } else {
                System.out.println("Please enter only \"y\" or \"n\"");
            }
        }
        return contribution;
    }

    public static double userStartingValue() { //What value the initial investment is
        boolean check = true;
        startingVar = 0;
        while (check) {
            try {
                System.out.println("Starting Amount: ");
                startingVar = scan.nextDouble(); //return variable
                check = false;
            } catch (Exception e) { //could use inputMismatch for more specific exception
                String str = scan.nextLine(); // this is needed because it clears a buffer that would otherwise cause an infinite loop
                System.out.println(str + " is not a valid number");
            }
        }
        return startingVar;
    }

    public static double userCompound() { //At what rate% they want it to compound
        boolean check = true;
        double retVar = 0;
        while (check) {
            try {
                System.out.println("Compound Value (%): ");
                retVar = scan.nextDouble();
                check = false;
            } catch (Exception e) {
                String str = scan.nextLine();
                System.out.println(str + " is not a valid number");
            }
        }
        return retVar / 100 + 1;
    }

    public static int tradingDays() { //how many days they want to do it for
        boolean check = true;
        tradingVar = 0;
        while (check) {
            try {
                System.out.println("Days expected to trade: ");
                tradingVar = scan.nextInt();
                check = false;
            } catch (Exception e) {
                System.out.println("Please Enter a Whole Number");
                scan.nextLine();
            }
        }
        return tradingVar;
    }

    public static int userAdd() //how often the user wants to deposit
    {
        boolean check = true;
        while(check) {
            System.out.println("Add Money Every: ");
            add = scan.nextLine();
            add = add.toLowerCase();
            if (add.equals("day") || add.equals("week") || add.equals("biweek") || add.equals("month") || add.equals("year")) {
                check = false;
                userAddValue();
            } else {
                System.out.println("Please enter a valid input");
            }
        }
        return switch (add) {
            case "day" -> 1;
            case "week" -> 7;
            case "biweek"-> 14;
            case "month" -> 30;
            case "year" -> 365;
            default -> 1;
        };
            }

    public static void  userAddValue() //how much the user wants to deposit depending on their frequency
    {
        boolean check = true;
        while(check) {
            try {
                System.out.println("How much money every " + add + "?");
                addValue = scan.nextDouble();
                check = false;
            } catch(Exception e)
            {
                String str = scan.nextLine();
                System.out.println(str + " is not a valid number");
            }
        }
    }
   public static void finalAmount()
  {
    System.out.println("");
    System.out.println("Final Amount - $" + numberFormat.format(finalAmount));
    if(contribution)
    {
      double finalVar = tradingVar / addingFrequency * addValue;    
      System.out.println("From Investments - $" + numberFormat.format((finalAmount - startingVar - finalVar)));
      System.out.println("From Deposits - $" + numberFormat.format(finalVar));
    } else {
      System.out.println("From Investments - $" + numberFormat.format((finalAmount - startingVar)));
    }
    System.out.println("Increase Of %" + numberFormat.format((finalAmount / startingVar * 100)));
  }
}
