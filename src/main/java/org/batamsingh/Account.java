package org.batamsingh;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Account {
    String accNo;
    String name;
    String dob;
    String address;
    String phone;
    double bal;
    public Account() {
        generateAccNo();
    }

    public Account(String name, String dob, String address, String phone) {
        generateAccNo();
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.bal = 2000;
    }

    private void generateAccNo() {
        int count = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
            Statement stm = connection.createStatement()) {

            String queryStr = "SELECT COUNT(*) FROM accounts";


            ResultSet resultSet = stm.executeQuery(queryStr);

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            this.accNo = "SAV" + String.format("%04d", (count+1));

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
            this.accNo = "SAV" + String.format("%04d", (count+1));
        }
    }

    public String getAccNo() {
        return accNo;
    }

    public void depositBal(double amount) {
        bal+=amount;
        updateBal();
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBal() {
        return bal;
    }

    public void setBal(double bal) {
        this.bal = bal;
    }
}
