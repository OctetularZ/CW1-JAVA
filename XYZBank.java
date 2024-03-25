import Customer.Customer;
import Record.Loan;
import Record.LoanTypes.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class XYZBank {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int maxRecords = 0;

        // The while loop below is used to ensure that the user enters a valid value for the max number of records.
        /* If the value they enter is less than 1 or is simply not an integer,
           then they will be asked for another input and will be given a prompt of the problem with their input.
        */
        // They will be asked to try again until they enter a valid input.
        while (maxRecords < 1) {
            try {
                System.out.print("Maximum number of records (1 or more): ");
                maxRecords = input.nextInt();
                if (maxRecords < 1) {
                    System.out.println("Maximum number of records must be greater than 0...");
                    System.out.println();
                }
            }
            catch (InputMismatchException err) { // Catches any input which isn't an integer.
                System.out.println("Invalid input for number of records!");
                System.out.println();
                input.nextLine();
            }
        }

        System.out.println();

        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<String> customerIDs = new ArrayList<>();

        int i = 0; // Counter for how many records have been added to the record list and where to store the next record.

        // While loop below is used to keep asking the user whether they want to add a record or display the records.
        // They also have the option to quit out of the program.
        while (true) {
            // The series of user inputs below will be used to create a new record, so it can be added to the record list.
            System.out.println("""
                    Choose an option:
                    'Add-C/Add-Customer' - Add a customer, 'Add-R/Add-Record' - Add a record,
                    'Display-All/Disp-A' - Display all records, 'Display/Disp' - Displays a customer's records,
                    'Update/Upd' - Update a customer's information,
                    'Remove/Rem' - Remove a record, 'Quit/Exit' - Exit program):\s""");
            String option = input.next();
            System.out.println();
            if (option.equalsIgnoreCase("add-c") || option.equalsIgnoreCase("add-customer")) {
                System.out.print("Enter the Customer ID (3 letters followed by 3 digits): ");
                String customerID = input.next();
                System.out.print("Enter the customer's annual income in thousands (i.e. '260' = £260,000): ");
                String customerIncome = input.next();
                System.out.println();

                if (customerIDs.contains(customerID.toUpperCase())) {
                    System.out.println("Customer ID already exists...");
                    System.out.println();
                }
                else {
                    var newCustomer = new Customer(customerID, customerIncome);
                    customers.add(newCustomer);
                    customerIDs.add(customerID.toUpperCase());

                    System.out.println("Customer Successfully Added!");
                    System.out.println();
                }

            }
            else if (option.equalsIgnoreCase("add-r") || option.equalsIgnoreCase("add-record")) {
                // Selecting which customer to add the record to
                System.out.print("Enter the customer ID of whom you would like to add the record to: ");
                String customerID = input.next();
                if (!customerIDs.contains(customerID.toUpperCase())) {
                    System.out.println("Customer ID doesn't exist...");
                    System.out.println();
                }
                else {
                    // Getting the customer's objects
                    Customer customer = null;
                    for (Customer customerObj : customers) {
                        if (customerObj.getCustomerID().equalsIgnoreCase(customerID)) {
                            customer = customerObj;
                        }
                    }

                    System.out.println("-----------------------------------------------------------------------------");
                    System.out.print("Enter the type of loan (Auto, Builder, Mortgage, Personal or Other): ");
                    String typeOfLoan = input.next();
                    typeOfLoan = Loan.setTypeOfLoan(typeOfLoan);

                    System.out.print("Enter the Record ID (6 digits/numbers): ");
                    String recordID = input.next();
                    System.out.print("Enter the interest rate (i.e. '30' = 30%): ");
                    String interestRate = input.next();
                    System.out.print("Enter the amount left to pay in thousands (i.e. '540' = £540,000): ");
                    String amountToPay = input.next();
                    System.out.print("Enter the loan term left in years (i.e. '2' = 2 years): ");
                    String loanTermLeft = input.next();
                    System.out.println();

                    Loan newRecord;
                    if (typeOfLoan.equalsIgnoreCase("auto")) {
                        newRecord = new Auto(recordID, interestRate, amountToPay, loanTermLeft);
                    }
                    else if (typeOfLoan.equalsIgnoreCase("builder")) {
                        System.out.print("Enter the overpayment amount in terms of percentage (a number between 0 and 2 - i.e. '1.2' = 1.2%): ");
                        String overpayment = input.next();
                        newRecord = new Builder(recordID, interestRate, amountToPay, loanTermLeft, overpayment);
                    }
                    else if (typeOfLoan.equalsIgnoreCase("mortgage")) {
                        System.out.print("Enter the overpayment amount in terms of percentage (a number between 0 and 2 - i.e. '1.2' = 1.2%): ");
                        String overpayment = input.next();
                        newRecord = new Mortgage(recordID, interestRate, amountToPay, loanTermLeft, overpayment);
                    }
                    else if (typeOfLoan.equalsIgnoreCase("other")) {
                        newRecord = new Other(recordID, interestRate, amountToPay, loanTermLeft);
                    }
                    else {
                        newRecord = new Personal(recordID, interestRate, amountToPay, loanTermLeft);
                    }

                    // Checking customer eligibility
                    assert customer != null;
                    if (!customer.checkEligibility(amountToPay, customer.getCustomerIncome())) {
                        System.out.println("Customer isn't eligible for a loan!");
                        System.out.println();
                        customer.setEligibility("NO");
                    }
                    else {
                        // The if statement below checks if the value of i is less than the max number of records.
                        // If it is, then a new record will be added using the user's inputted values.
                        // Otherwise, the user will receive a message to let them know that the maximum number of records has been reached, and they cannot add anymore records.
                        if (i < maxRecords) {
                            customer.addToRecords(newRecord);
                            i++;
                            System.out.println("Record Successfully Added!");
                            System.out.println();
                            customer.setEligibility("YES");
                        }
                        else {
                            System.out.println("Maximum records stored!");
                            System.out.println();
                        }
                    }
                }

            }
            // The else if block below uses the java Formatter to display all the records in the records list when the 'display' option is selected by the user.
            else if (option.equalsIgnoreCase("display") || option.equalsIgnoreCase("disp")) {
                // Selecting which customer records you want displayed
                if (customerIDs.isEmpty()) {
                    System.out.println("There are currently no registered customers...");
                    System.out.println();
                }
                else {
                    System.out.print("Enter the customer ID of whom you would like their records displayed: ");
                    String customerID = input.next();

                    if (!customerIDs.contains(customerID.toUpperCase())) {
                        System.out.println("Customer ID doesn't exist...");
                        System.out.println();
                    } else {
                        // Getting the customer's objects
                        Customer customer = null;
                        for (Customer customerObj : customers) {
                            if (customerObj.getCustomerID().equalsIgnoreCase(customerID)) {
                                customer = customerObj;
                            }
                        }
                        System.out.println();
                        System.out.println("Maximum number of Records: " + maxRecords);
                        assert customer != null;
                        if (!customer.getCreditRecords().isEmpty()) {
                            System.out.println("Registered Records: " + Loan.recordIDList.size());
                        }
                        customer.printDetails(customer);
                        System.out.println();
                    }
                }
            }
            else if (option.equalsIgnoreCase("display-all") || option.equalsIgnoreCase("disp-a")) {
                if (Loan.recordIDList.isEmpty()) {
                    System.out.println("There are currently no records stored...");
                    System.out.println();
                }
                else {
                    System.out.println("Maximum number of Records: " + maxRecords);
                    if (!Loan.recordIDList.isEmpty()) {
                        System.out.println("Registered Records: " + Loan.recordIDList.size());
                    }
                    for (Customer customer : customers) {
                        if (!customer.getCreditRecords().isEmpty()){
                            customer.printDetails(customer);
                            System.out.println();
                        }
                    }
                }
            }
            else if (option.equalsIgnoreCase("remove") || option.equalsIgnoreCase("rem")) {
                // Selecting which customer's records you want to remove
                if (Loan.recordIDList.isEmpty()) {
                    System.out.println("There are currently no records stored...");
                    System.out.println();
                }
                else {
                    System.out.print("Enter the record ID of the record you would like to remove: ");
                    String recordID = input.next();

                    if (!Loan.recordIDList.contains(recordID.toUpperCase())) {
                        System.out.println("Record ID doesn't exist...");
                        System.out.println();
                    } else {
                        for (Customer customer : customers) {
                            customer.getCreditRecords().removeIf(record -> record.getRecordID().equalsIgnoreCase(recordID));
                        }
                        Loan.recordIDList.remove(recordID.toUpperCase());
                        i--;
                    }
                }
            }
            else if (option.equalsIgnoreCase("update") || option.equalsIgnoreCase("upd")) {
                if (customerIDs.isEmpty()) {
                    System.out.println("There are currently no registered customers...");
                    System.out.println();
                }
                else {
                    System.out.print("Enter the customer ID of whom you would like to update their info: ");
                    String customerID = input.next();

                    if (!customerIDs.contains(customerID.toUpperCase())) {
                        System.out.println("Customer ID doesn't exist...");
                        System.out.println();
                    } else {
                        // Getting the customer's objects
                        Customer customer = null;
                        for (Customer customerObj : customers) {
                            if (customerObj.getCustomerID().equalsIgnoreCase(customerID)) {
                                customer = customerObj;
                            }
                        }
                        System.out.println();
                        System.out.println("Which of these customer's data would you like to update?");
                        String updateData = input.next();
                        if (updateData.equalsIgnoreCase("customer-id") || updateData.equalsIgnoreCase("c-id")) {
                            System.out.print("Enter the new Customer ID (3 letters followed by 3 digits): ");
                            String newCustomerID = input.next();

                            if (customerIDs.contains(newCustomerID.toUpperCase())) {
                                System.out.println("Customer ID already exists...");
                                System.out.println();
                            }
                            else {
                                assert customer != null;
                                customerIDs.remove(customerID.toUpperCase());
                                customer.setCustomerID(newCustomerID);
                                customerIDs.add(newCustomerID.toUpperCase());

                                System.out.println("Customer ID Successfully Updated!");
                                System.out.println();
                            }
                        }
                        else if (updateData.equalsIgnoreCase("customer-income") || updateData.equalsIgnoreCase("c-inc")) {
                            System.out.print("Enter the customer's new annual income in thousands (i.e. '260' = £260,000): ");
                            String newCustomerIncome = input.next();

                            assert customer != null;
                            customer.setCustomerIncome(newCustomerIncome);

                            System.out.println("Customer Income Successfully Updated!");
                            System.out.println();
                        }
                        else if (updateData.equalsIgnoreCase("eligibility") || updateData.equalsIgnoreCase("elig")) {
                            System.out.print("Enter the customer's new eligibility status ('Yes' or 'No'): ");
                            String newCustomerEligibilityStatus = input.next();

                            assert customer != null;
                            customer.setEligibility(newCustomerEligibilityStatus);

                            System.out.println("Customer Eligibility Status Successfully Updated!");
                            System.out.println();
                        }
                        else {
                            System.out.println("There is no such value to update...");
                            System.out.println();
                        }
                    }
                }
            }
            else if (option.equalsIgnoreCase("help")) {
                System.out.println(" ");
            }
            // Breaks out of the loop and exits the program if the user selects the 'quit' option
            else if (option.equalsIgnoreCase("quit") || option.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }
            else {
                // Tells the user that the option they entered is invalid if they don't input any other valid options.
                System.out.println("Invalid option!");
            }
        }
    }
}
