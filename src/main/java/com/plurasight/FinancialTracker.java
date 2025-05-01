package com.plurasight;

import com.plurasight.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions (1).csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Type here: ");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 5) {
                        LocalDate date = LocalDate.parse(parts[0]);
                        LocalTime time = LocalTime.parse(parts[1]);
                        String description = parts[2];
                        String vendor = parts[3];
                        double amount = Double.parseDouble(parts[4]);
                        transactions.add(new Transaction(date, time, description, vendor, amount));
                    }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // The transactions should be stored in the `transactions` ArrayList.
        // Each line of the file represents a single transaction in the following format:
        // <date>|<time>|<description>|<vendor>|<amount>
        // For example: 2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.
    }

    private static void addDeposit(Scanner scanner) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid input try again");
            return;
        }
        scanner.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);
        System.out.println(date);
        System.out.println(time.format(dtf.ofPattern("HH:mm:ss")));

        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);

        String csvLine = String.format("%s|%s|%s|%s|%.2f", date, time.format(dtf.ofPattern("HH:mm:ss")), description, vendor, amount);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(csvLine);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        //Transaction transactions = new Transaction(date, time, description, vendor, amount);
        //transactions.add(new Transaction(date, time, description, vendor, amount));
        //System.out.println();

        /*try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(String.valueOf(transactions));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // This method should prompt the user to enter the date, time, description, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Transaction` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.
    }

    private static void addPayment(Scanner scanner) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        double adjustedAmount = amount * -1;
        if (adjustedAmount >= 0) {
            System.out.println("Invalid input try again");
            return;
        }
        scanner.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);
        System.out.println(date);
        System.out.println(time.format(dtf.ofPattern("HH:mm:ss")));

        Transaction newTransaction = new Transaction(date, time, description, vendor, adjustedAmount);
        transactions.add(newTransaction);

        String csvLine = String.format("%s|%s|%s|%s|%.2f", date, time.format(dtf.ofPattern("HH:mm:ss")), description, vendor, adjustedAmount);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(csvLine);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // This method should prompt the user to enter the date, time, description, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount received should be a positive number then transformed to a negative number.
        // After validating the input, a new `Transaction` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Type here: ");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }
        System.out.printf("%-12s %-10s %-30s %-20s %-10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        //while ((line = Transaction.readLine()) != null) {
        for (Transaction transaction : transactions) {
            System.out.printf("%-12s %-10s %-30s %-20s %-10s%n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
        }

        //reader.close();
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
    }

    private static void displayDeposits() {
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }
        System.out.printf("%-12s %-10s %-30s %-20s %-10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        //while ((line = Transaction.readLine()) != null) {
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.printf("%-12s %-10s %-30s %-20s %-10s%n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
    }

    private static void displayPayments() {
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }
        System.out.printf("%-12s %-10s %-30s %-20s %-10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        //while ((line = Transaction.readLine()) != null) {
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.printf("%-12s %-10s %-30s %-20s %-10s%n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }

        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Type here: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.println("Current Month Report");
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1), LocalDate.now());
                    break;
                    // Generate a report for all transactions within the current month,
                    // including the date, time, description, vendor, and amount for each transaction.
                case "2":
                    System.out.println("Previous Month Report");
                    LocalDate startOfPrevMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate endOfPrevMonth = startOfPrevMonth.withDayOfMonth(startOfPrevMonth.lengthOfMonth());
                    filterTransactionsByDate(startOfPrevMonth, endOfPrevMonth);
                    break;
                    // Generate a report for all transactions within the previous month,
                    // including the date, time, description, vendor, and amount for each transaction.
                case "3":
                    System.out.println("Current Year Report");
                    filterTransactionsByDate(LocalDate.now().withDayOfYear(1), LocalDate.now());
                    break;
                    // Generate a report for all transactions within the current year,
                    // including the date, time, description, vendor, and amount for each transaction.

                case "4":
                    System.out.println("Previous Month Report");
                    LocalDate startOfPrevYear = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate endOfPrevYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);
                    filterTransactionsByDate(startOfPrevYear, endOfPrevYear);
                    break;
                    // Generate a report for all transactions within the previous year,
                    // including the date, time, description, vendor, and amount for each transaction.
                case "5":
                    System.out.print("Enter the vendor name: ");
                    String vendor = scanner.nextLine();
                    filterTransactionsByVendor(vendor);
                    break;
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, time, description, vendor, and amount for each transaction.
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        boolean found = false;

        for (Transaction transaction : transactions) {
            // Parse the date string from the transaction

            // Check if the transaction date is within the given range (inclusive)
            if ((transaction.getDate().isEqual(startDate) || transaction.getDate().isAfter(startDate)) &&
                    (transaction.getDate().isEqual(endDate) || transaction.getDate().isBefore(endDate))) {

                // Print the transaction details
                System.out.println(transaction);

                // Indicate that at least one matching transaction was found
                found = true;
            }
        }

        // If no matching transactions were found
        if (!found) {
            System.out.println("No transactions found between those dates.");
        }
    }
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.

    private static void filterTransactionsByVendor(String vendor) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    try {
                        String vendorLike = parts[3];
                        if (vendorLike.equalsIgnoreCase(vendor)) {
                            System.out.println(line);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number in amount column: " + parts[3]);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }

        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
    }
}

