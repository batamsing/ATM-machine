package org.batamsingh;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class AdminOperation {
    private static final String DATABASE_URL = "jdbc:sqlite:mydatabase.db";
    private static HashMap<String, Atm> accounts = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadAcc();

        int x = 1;
        do {
            int choice = chooseOperation();
            sc.nextLine();
            switch (choice) {
                case 1:
                    // adding account details
                    addAccount();
                    break;
                case 2:
                    // viewing account details by entering account number
                    viewAccountNumber();
                    break;
                case 3:
                    // removing account
                    System.out.print("Enter the account number: ");
                    removeAccount(sc.nextLine());
                case 4:
                    // adding bal
                    System.out.print("Enter the account number: ");
                    addBal(sc.nextLine());
                    break;
                case 5:
                    // updating account
                    System.out.print("Enter account number: ");
                    updateAccount(sc.nextLine());
                case 6:
                    //Viewing all accounts
                    System.out.println(accounts);
                    break;
                case 7:
                    x = 2;
                    break;
            }

        } while (x!=2);
    }

    private static void updateAccount(String s) {
        if (accounts.containsKey(s)) {
            int x = 1;
            do {
                int choice;
                System.out.println("Enter your choice: ");
                System.out.println("1. Change Name");
                System.out.println("2. Change Address");
                System.out.println("3. Change Phone");
                System.out.println("4. Change DOB");
                System.out.println("5. Exit!");

                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // changing name
                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine();
                        accounts.get(s).setName(newName);
                        break;
                    case 2:
                        // changing address
                        System.out.print("Enter new Address: ");
                        String newAddress = sc.nextLine();
                        accounts.get(s).setAddress(newAddress);
                        break;
                    case 3:
                        // change phone
                        System.out.print("Enter new Phone number: ");
                        String newPhone = sc.nextLine();
                        accounts.get(s).setPhone(newPhone);
                        break;
                    case 4:
                        // change dob
                        System.out.print("Enter new DOB: ");
                        String newDob = sc.nextLine();
                        accounts.get(s).setDob(newDob);
                        break;
                    case 5:
                        x = 2;
                        // exit
                        break;
                }
            } while (x!=1);

        }
    }

    private static void addBal(String s) {
        if (accounts.containsKey(s)) {

            System.out.print("Enter the amount to deposit: ");
            double amount = sc.nextDouble();
            depositBalToDatabase(amount);
            accounts.get(s).depositBal(amount);
            System.out.println("Deposit Successful");
            System.out.print("Available Balance: " + accounts.get(s).getBal());
        }
    }

    private static void depositBalToDatabase(double amount) {
        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stm = con.prepareStatement("UPDATE accounts SET bal WHERE accNo = ?")) {

            stm.setDouble(1, amount);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void removeAccount(String s) {
        if (accounts.containsKey(s)) {
            removeFromDatabase(s);
            accounts.remove(s);
            System.out.println("Account removed");
        }

        else System.out.println("Can't find account!");
    }

    private static void removeFromDatabase(String s) {
        try (Connection con = DriverManager.getConnection(DATABASE_URL);
        PreparedStatement stm = con.prepareStatement("DELETE FROM accounts WHERE accNo = ?")) {

            stm.setString(1,s);
            stm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewAccountNumber() {
        System.out.print("Enter account number: ");
        String acNo = sc.nextLine();

        if (accounts.containsKey(acNo)) {
            System.out.println(accounts.get(acNo));
        } else System.out.println("Oops! No records found!");
    }

    private static void addAccount() {
        System.out.println("Input account details:");
        String name, dob, address, phone;
        double bal;
        System.out.println("Enter name");
        name = sc.nextLine();
        System.out.println("Enter DOB");
        dob = sc.nextLine();
        System.out.println("Enter Address");
        address = sc.nextLine();
        System.out.println("Enter Phone number");
        phone = sc.nextLine();

        Atm atm = new Atm(name, dob, address, phone);

        accounts.put(atm.getAccNo(), atm);
        // needs to add to the database also
        addAccountToDatabase(atm);
        System.out.println("Successfully created account for " + name);
        System.out.println("Account number: " + atm.getAccNo() + "   Pin No.: " + atm.getPin());
    }

    private static void addAccountToDatabase(Atm atm) {
        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO accounts (accNo, name, dob, address, phone, bal, pin) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, atm.getAccNo());
            preparedStatement.setString(2, atm.getName());
            preparedStatement.setString(3, atm.getDob());
            preparedStatement.setString(4, atm.getAddress());
            preparedStatement.setString(5, atm.getPhone());
            preparedStatement.setDouble(6, atm.getBal());
            preparedStatement.setString(7, atm.getPin());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static int chooseOperation() {
        System.out.println("Choose your operation:");
        System.out.println("1. Add Account");
        System.out.println("2. View Account");
        System.out.println("3. Remove Account");
        System.out.println("4. Add Balance");
        System.out.println("5. Update Account");
        System.out.println("6. View All Account");
        System.out.println("7. Exit");

        return sc.nextInt();
    }

    private static void loadAcc() {
        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             Statement stm = con.createStatement()) {

            // Create a Table
            String createTable = "CREATE TABLE IF NOT EXISTS accounts (" +
                    "accNo TEXT PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "dob TEXT," +
                    "address TEXT," +
                    "phone TEXT," +
                    "bal DOUBLE," +
                    "pin TEXT)";

            stm.execute(createTable);

            String selectQuery = "SELECT * FROM accounts";
            ResultSet resultSet = stm.executeQuery(selectQuery);

            while (resultSet.next()) {
                String accountNumber = resultSet.getString("accNo");
                String name = resultSet.getString("name");
                String dob = resultSet.getString("dob");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                double bal = resultSet.getDouble("bal");
                String pin = resultSet.getString("pin");

                Atm account = new Atm(accountNumber, name, dob, address, phone, bal, pin);
                accounts.put(account.accNo, account);
            }
            System.out.println("Accounts table created successfully!");
        } catch (SQLException e) {
            System.out.println("Can't load account!");
            e.printStackTrace();
        }
    }
}
