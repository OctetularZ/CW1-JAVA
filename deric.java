import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Xyzbank {
    private static Map<String, Integer> customerIDs = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int maxRecords = 0;

        while (maxRecords <= 0 || maxRecords > 100) {
            System.out.print("What's the desired number of records you want to enter: ");
            if (scanner.hasNextInt()) {
                maxRecords = scanner.nextInt();
                if (maxRecords <= 0 || maxRecords > 100) {
                    System.out.println("Invalid number of records.");
                    System.out.println("Please enter a number between 1 and 100.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the buffer
            }
        }

        Record2[] records = new Record2[maxRecords];
        int count = 0;
        int option = 0; // Initialize option with a default value

        // Options menu
        do {
            System.out.println("\nChoose an option:");
            System.out.println("1. Enter your Record(s)");
            System.out.println("2. Print Record(s)");
            System.out.println("3. Print particular Record");
            System.out.println("4. Terminate");

            // Ensure the input is an integer
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        if (count < maxRecords) {
                            System.out.println("Enter details for Record " + (count + 1) + ":");
                            // Prompt for Record ID
                            System.out.print("Record ID (max 6 characters, numbers only): ");
                            String recordID;
                            while (true) {
                                recordID = scanner.next();
                                if (recordID.matches("\\d{6,6}")) {
                                    if (customerIDs.containsKey(recordID)) {
                                        System.out.println("Record ID already exists. Please enter a unique one.");
                                    } else {
                                        customerIDs.put(recordID, 1);
                                        break;
                                    }
                                } else {
                                    System.out.println("Invalid input. Record ID should contain only numbers and be maximum 6 characters.");
                                }
                            }

                            System.out.print("First Name: ");
                            String firstName = scanner.next();
                            while (!firstName.matches("[a-zA-Z]{2,}")) {
                                System.out.println("Invalid input. First name should contain at least 2 letters.");
                                System.out.print("First Name: ");
                                firstName = scanner.next();
                            }

                            System.out.print("Last Name: ");
                            String lastName = scanner.next();
                            while (!lastName.matches("[a-zA-Z]{2,}")) {
                                System.out.println("Invalid input. Last name should contain at least 2 letters.");
                                System.out.print("Last Name: ");
                                lastName = scanner.next();
                            }

                            // Generate customerID
                            String initials = firstName.substring(0, 1).toUpperCase() + lastName.substring(0, 1).toUpperCase();
                            int num = customerIDs.get(recordID);
                            String customerID = generateCustomerID(initials, num);

                            // Assuming customerID is generated automatically
                            customerIDs.put(recordID, num + 1);

                            // Prompt for loan type
                            String loanType;
                            while (true) {
                                System.out.println("Enter loan type (Auto/Builder/Mortgage/Personal/Other):");
                                loanType = scanner.next();
                                if (loanType.matches("(?i)Auto|Builder|Mortgage|Personal|Other")) {
                                    break;
                                } else {
                                    System.out.println("Invalid input. Please enter a valid loan type.");
                                }
                            }

                            // Prompt and validate interest rate
                            double interestRate = 0.0;
                            while (true) {
                                System.out.println("Enter interest rate:");
                                if (scanner.hasNextDouble()) {
                                    interestRate = scanner.nextDouble();
                                    if (interestRate > 0) {
                                        break;
                                    } else {
                                        System.out.println("Interest rate must be greater than 0.");
                                    }
                                } else {
                                    System.out.println("Invalid input. Please enter a valid interest rate.");
                                    scanner.next(); // Clear the buffer
                                }
                            }

                            // Prompt and validate amount left to pay
                            double amountLeftToPay = 0.0;
                            while (true) {
                                System.out.println("Enter amount left to pay (in thousands pounds):");
                                if (scanner.hasNextDouble()) {
                                    amountLeftToPay = scanner.nextDouble();
                                    if (amountLeftToPay > 0) {
                                        break;
                                    } else {
                                        System.out.println("Amount left to pay must be greater than 0.");
                                    }
                                } else {
                                    System.out.println("Invalid input. Please enter a valid amount.");
                                    scanner.next(); // Clear the buffer
                                }
                            }

                            // Prompt and validate loan term left
                            int loanTermLeft = 0;
                            while (true) {
                                System.out.println("Enter loan term left (in years):");
                                if (scanner.hasNextInt()) {
                                    loanTermLeft = scanner.nextInt();
                                    if (loanTermLeft >= 1) {
                                        break;
                                    } else {
                                        System.out.println("Loan term left must be at least 1 year.");
                                    }
                                } else {
                                    System.out.println("Invalid input. Please enter a valid loan term.");
                                    scanner.next(); // Clear the buffer
                                }
                            }

                            records[count] = new Record2(recordID, firstName, lastName, customerID, loanType, interestRate, amountLeftToPay, loanTermLeft);
                            count++;
                        } else {
                            System.out.println("Maximum number of records reached.");
                        }
                        break;
                    case 2:
                        if (count > 0) {
                            // Print all records
                            for (int i = 0; i < count; i++) {
                                System.out.println(records[i]);
                            }
                        } else {
                            System.out.println("No records to print.");
                        }
                        break;
                    case 3:
                        // Print particular record
                        System.out.println("Enter the record number (1-" + count + "):");
                        int recordNumber = scanner.nextInt();
                        if (recordNumber >= 1 && recordNumber <= count) {
                            System.out.println(records[recordNumber - 1]);
                        } else {
                            System.out.println("Invalid record number.");
                        }
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Invalid option. Please enter a valid number.");
                scanner.next(); // Clear the buffer
            }
        } while (option != 4);

        scanner.close();
    }

    private static String generateCustomerID(String initials, int num) {
        return initials + String.format("%04d", num);
    }
}

class Record2 {
    private String recordID;
    private String firstName;
    private String lastName;
    private String customerID;
    private String loanType;
    private double interestRate;
    private double amountLeftToPay;
    private int loanTermLeft;

    public Record2(String recordID, String firstName, String lastName, String customerID, String loanType, double interestRate, double amountLeftToPay, int loanTermLeft) {
        this.recordID = recordID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerID = customerID;
        this.loanType = loanType;
        this.interestRate = interestRate;
        this.amountLeftToPay = amountLeftToPay;
        this.loanTermLeft = loanTermLeft;
    }

    @Override
    public String toString() {
        return String.format("Record ID: %s, First Name: %s, Last Name: %s, Loan Type: %s, Interest Rate: %.2f, Amount Left to Pay: %.2f, Loan Term Left: %d",
                customerID, firstName, lastName, loanType, interestRate, amountLeftToPay, loanTermLeft);
    }
}