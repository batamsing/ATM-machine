package org.batamsingh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Atm extends Account {
    private String pin;

    // todo
    public Atm() {
        generatePin();
    }

    public Atm(String name, String dob, String address, String phone) {
        super(name, dob, address, phone);
        generatePin();
    }

    public Atm(String accountNumber, String name, String dob, String address, String phone, Double bal, String pin) {
        this.accNo = accountNumber;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.bal = bal;
        this.pin = pin;
    }

    public void changePin(String pin, String newPin) {
        if (verifyPin(pin)) {
            setPin(newPin);
            System.out.println("Pin change successfully");
        }
        else System.out.println("Can't change pin!");
    }

    public boolean verifyPin(String pin) {
        return this.pin.equals(pin);
    }

    private void setPin(String pin) {
        this.pin = pin;
    }

    public void withdrawBal(double amount) {
        if (amount <= getBal()) {
            bal = bal - amount;
            updateBal();

            System.out.println("WITHDRAWAL SUCCESSFUL");
            System.out.println("Remaining balance: " + getBal());
        }
        else System.out.println("Withdrawal amount cannot be greater than available balance.");
    }

    private void updateBal() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE acNo = ?")) {

            preparedStatement.setDouble(1, bal);
            preparedStatement.setString(2, accNo);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generatePin() {
        Random random = new Random();
        pin = String.format("%04d", random.nextInt(10000));
    }

    public String getPin() {
        return pin;
    }

    public String toString() {
        return "Account No: " + getAccNo() +
                "\nBalance: " + getBal() +
                "\nName: " + getName() +
                "\nAddress: " + getName() +
                "\nDOB: " + getDob() +
                "\nPhone: " + getPhone() +
                "\nPin: " + getPin();
    }
}
