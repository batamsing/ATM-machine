package org.batamsingh;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
     private static final String DATABASE_URL = "jdbc:sqlite:mydatabase.db";
     private static HashMap<String, Atm> accounts = new HashMap<>();
     private static Scanner sc = new Scanner(System.in);

    private static Atm atm;
    public static void main(String[] args) {
        loadAccounts();

        // todo create perform operation

            System.out.println("Welcome to ATM machine");
            System.out.println("**********************");

            loginAccount();
        int y = 1;
        while (y != 0) {
            System.out.println("SELECT YOUR CHOICE");

            int choice;
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Balance");
            System.out.println("3. Deposit Balance");
            System.out.println("4. Change Pin");
            System.out.println("5. Exit");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // checking bal
                    System.out.println("Your Bal is " + atm.getBal());
                    break;
                case 2:
                    //
                    System.out.println("Please enter the amount you want to withdraw: ");
                    atm.withdrawBal(sc.nextDouble());
                    break;
                case 3:
                    System.out.println("Please enter the amount you want to deposit: ");
                    atm.depositBal(sc.nextDouble());
                    System.out.println("Deposit Successful!\nRemaining bal is: " + atm.getBal());
                    break;
                case 4:
                    System.out.println("Enter your old pin: ");
                    String oldPin = sc.nextLine();
                    System.out.println("Enter your new pin: ");
                    String newPin = sc.nextLine();

                    atm.changePin(oldPin, newPin);
                    break;
                case 5:
                    y = 0;

            }
        }


    }

    private static void loginAccount() {
        int x = 2;
        do {

            System.out.println("Please enter your account number: ");
            String enterAcc = sc.nextLine();

            if (accounts.containsKey(enterAcc)) {
                atm = accounts.get(enterAcc);
                System.out.println("Enter you 4 digit pin");

                String enterPin = sc.nextLine();
                if (atm.verifyPin(enterPin)) {

                    System.out.println("LogIn Success!");
                } else {
                    System.out.println("Invalid Pin!");
                    x = 1;
                }


            }
            else {
                System.out.println("Wrong Input Account number!");
                x = 1;
            }
        } while (x == 1);


    }

    private static void loadAccounts() {
        try (Connection con = DriverManager.getConnection(DATABASE_URL);
             Statement stm = con.createStatement()) {

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
            e.printStackTrace();
        }
    }
}