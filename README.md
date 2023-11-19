# ATM Machine Project
Welcome to the ATM Machine project! This Java-based project simulates the functionality of an Automated Teller Machine (ATM) with two main classes: AdminOperation and AtmOperation. The AdminOperation class allows administrators to perform tasks such as adding accounts, depositing money, removing accounts, updating account details, and viewing accounts. The AtmOperation class is designed for users to interact with the ATM, enabling actions like withdrawing money, transferring funds, depositing money, and changing their PIN.

## Project Structure
<<<<<<< HEAD
**AdminOperation.java:** The main class for administrative operations.

**Main.java:** The main class for ATM operations.
=======
AdminOperation.java: The main class for administrative operations.
Atm.java: The main class for ATM operations.
>>>>>>> 78238f309015c64fe9a593870b7cdaa73d0a0323

## Features:
### AdminOperation
**Add Account:** Administrators can input account details, including name, date of birth, address, and phone number, to create new accounts.

**Deposit:** Deposit money into user accounts.

**Remove Account:** Remove accounts by providing the account number.

**Update Account:** Update account details, such as name, address, phone, and date of birth.

**View Accounts:** View details of all accounts in the system.
### AtmOperation
**Withdraw Money:** Users can withdraw money from their accounts by providing the necessary details.

**Transfer Funds:** Transfer money between accounts.

**Deposit Money:** Deposit money into user accounts.

**Change PIN:** Change the PIN by validating the account number and existing PIN.


## Technologies Used:
**Java:** Core programming language.

**Hibernate:** Object-relational mapping framework for database interactions.

**SQLite:** Lightweight, embedded relational database for data storage.

**JDBC:** Java Database Connectivity for database interaction.

**Maven:** Project management and build tool.

## Getting Started:
To run the ATM Machine project locally:

1. Clone the repository:

    ```bash
    git clone https://github.com/batamsing/atm-machine.git
    ```

2. Navigate to the project directory:

    ```bash
    cd atm-machine
    ```

3. Compile and run the application:

    ```bash
    javac AdminOperation.java Main.java
    java AdminOperation
    ```
