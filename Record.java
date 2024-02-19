package Record;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Record {
    Scanner input = new Scanner(System.in);
    private String recordID;
    private String customerID;
    private String typeOfLoan;
    private String interestRate;
    private String amountToPay;
    private String loanTermLeft;

    public Record(String recordID, String customerID, String typeOfLoan, String interestRate, String amountToPay, String loanTermLeft) {
        setRecordID(recordID);
        setCustomerID(customerID);
        setTypeOfLoan(typeOfLoan);
        setInterestRate(interestRate);
        setAmountToPay(amountToPay);
        setLoanTermLeft(loanTermLeft);
    }

    private boolean isStringOnlyAlphabet(String str) {
        return ((!str.isEmpty())
                && (str != null)
                && (str.matches("^[a-zA-Z]*$")));
    }

    private boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("\\d+");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private boolean isNumericOrFloat(String strNum) {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public void setRecordID(String newRecordID) {
        boolean isInteger = isNumeric(newRecordID);
        while (newRecordID.length() != 6 || !isInteger) {
            System.out.println("Invalid record ID format");
            System.out.print("Enter the Record ID (6 digits/numbers): ");
            newRecordID = input.next();
            System.out.println();
            isInteger = isNumeric(newRecordID);
        }
        this.recordID = newRecordID;
    }

    public String getRecordID() {
        return this.recordID;
    }

    public void setCustomerID(String newCustomerID) {
        String firstHalf = newCustomerID.substring(0, 3);
        String secondHalf = newCustomerID.substring(3);

        boolean isInteger = isNumeric(secondHalf);
        boolean isAlphabet = isStringOnlyAlphabet(firstHalf);

        while (newCustomerID.length() != 6 || !isInteger || !isAlphabet) {
            System.out.println("Invalid customer ID format");
            System.out.print("Enter the customer ID (3 letters followed by 3 digits): ");
            newCustomerID = input.next();
            System.out.println();
            firstHalf = newCustomerID.substring(0, 3);
            secondHalf = newCustomerID.substring(3);
            isAlphabet = isStringOnlyAlphabet(firstHalf);
            isInteger = isNumeric(secondHalf);
        }
        this.customerID = newCustomerID.toUpperCase();
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setTypeOfLoan(String newTypeOfLoan) {
        ArrayList<String> typesOfLoans = new ArrayList<String>();
        typesOfLoans.add("auto");
        typesOfLoans.add("builder");
        typesOfLoans.add("mortgage");
        typesOfLoans.add("personal");
        typesOfLoans.add("other");

        while (!typesOfLoans.contains(newTypeOfLoan.toLowerCase())) {
            System.out.println("Invalid type of loan entered!");
            System.out.print("Enter the type of loan (Auto, Builder, Mortgage, Personal or Other): ");
            newTypeOfLoan = input.next();
            System.out.println();
        }
        this.typeOfLoan = newTypeOfLoan;
    }

    public String getTypeOfLoan() {
        return this.typeOfLoan;
    }

    public void setInterestRate(String newInterestRate) {
        boolean isFloat = isNumericOrFloat(newInterestRate);
        while (!isFloat) {
            System.out.println("Invalid interest rate entered!");
            System.out.print("Enter the interest rate (i.e. '30' = 30%): ");
            newInterestRate = input.next();
            System.out.println();
            isFloat = isNumericOrFloat(newInterestRate);
        }
        this.interestRate = newInterestRate;
    }

    public String getInterestRate() {
        return this.interestRate;
    }

    public void setAmountToPay(String newAmountToPay) {
        boolean isInteger = isNumeric(newAmountToPay);
        while (!isInteger) {
            System.out.println("Invalid amount to pay entered!");
            System.out.print("Enter the amount left to pay in thousands (i.e. '540' = £540,000): ");
            newAmountToPay = input.next();
            System.out.println();
            isInteger = isNumeric(newAmountToPay);
        }
        this.amountToPay = newAmountToPay;
    }

    public String getAmountToPay() {
        return this.amountToPay;
    }

    public void setLoanTermLeft(String newLoanTermLeft) {
        boolean isFloat = isNumericOrFloat(newLoanTermLeft);
        while (!isFloat) {
            System.out.println("Invalid loan term left entered!");
            System.out.print("Enter the loan term left in years (i.e. '2' = 2 years): ");
            newLoanTermLeft = input.next();
            System.out.println();
            isFloat = isNumericOrFloat(newLoanTermLeft);
        }
        this.loanTermLeft = newLoanTermLeft;
    }

    public String getLoanTermLeft() {
        return this.loanTermLeft;
    }
}
