import Record.Record;

import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class XYZBank {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int maxRecords = 0;

        while (maxRecords < 1) {
            try {
                System.out.print("Maximum number of records (1 or more): ");
                maxRecords = input.nextInt();
                if (maxRecords < 1) {
                    System.out.println("Maximum number of records must be greater than 0...");
                    System.out.println();
                }
            }
            catch (InputMismatchException err) {
                System.out.println("Invalid input for number of records!");
                System.out.println();
                input.nextLine();
            }
        }

        Record[] recordList = new Record[maxRecords];
        int i = 0;

        while (true) {
            System.out.print("Choose an option ('Add' - Add a record, 'Display' - Displays records): ");
            String option = input.next();
            System.out.println();
            if (option.equalsIgnoreCase("add")) {
                System.out.print("Enter the Record ID (6 digits/numbers): ");
                String recordID = input.next();
                System.out.print("Enter the Customer ID (3 letters followed by 3 digits): ");
                String customerID = input.next();
                System.out.print("Enter the type of loan (Auto, Builder, Mortgage, Personal or Other): ");
                String typeOfLoan = input.next().toLowerCase();
                System.out.print("Enter the interest rate (i.e. '30' = 30%): ");
                String interestRate = input.next();
                System.out.print("Enter the amount left to pay in thousands (i.e. '540' = £540,000): ");
                String amountToPay = input.next();
                System.out.print("Enter the loan term left in years (i.e. '2' = 2 years): ");
                String loanTermLeft = input.next();
                System.out.println();
                System.out.println("Record Successfully Added!");
                System.out.println();

                var newRecord = new Record(recordID, customerID, typeOfLoan, interestRate, amountToPay, loanTermLeft);
                if (i < recordList.length) {
                    recordList[i] = newRecord;
                    i++;
                }
                else {
                    System.out.println("Maximum records stored!");
                    System.out.println();
                }

            }
            else if (option.equalsIgnoreCase("display")) {
                Formatter fmt = new Formatter();
                fmt.format("%-10s %-10s %-10s %-10s %-10s %-10s\n", "RecordID", "CustomerID", "LoanType", "IntRate", "AmountLeft", "TimeLeft");
                if (recordList[0] == null) {
                    System.out.println("There are currently no records...");
                }
                else {
                    System.out.println("Maximum number of Records: " + maxRecords);
                    System.out.println("Registered Records: " + recordList.length);
                    System.out.println();
                    for (Record record : recordList) {
                        try {
                            fmt.format("%-10s %-10s %-10s %-10s %-10s %-10s\n", record.getRecordID(), record.getCustomerID(), record.getTypeOfLoan(), record.getInterestRate(), record.getAmountToPay(), record.getLoanTermLeft());
                        }
                        catch (NullPointerException ignored) {
                        }
                    }
                    System.out.println(fmt);
                }
            }
            else if (option.equalsIgnoreCase("quit")) {
                System.out.println("Exiting...");
                break;
            }
            else {
                System.out.println("Invalid option!");
            }
        }
    }
}

// Add comments