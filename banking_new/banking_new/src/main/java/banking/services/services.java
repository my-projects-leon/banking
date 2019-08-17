package banking.services;

import banking.Account;
import banking.Profile;
import banking.dao.AccountDao;
import banking.dao.AccountDaoJdbc;
import banking.dao.ProfileDao;
import banking.dao.ProfileDaoJdbc;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class services {

    private static ProfileDao profileDao = new ProfileDaoJdbc();
    private static AccountDao accountDao = new AccountDaoJdbc();

    public void userLogin() {
        Profile foundProfile = null;
        String username;
        Scanner reader = new Scanner(System.in);
        while (foundProfile == null) {
            System.out.println("Please enter in your username or press 4 to return to start menu:");
            username = reader.nextLine();
            if (username == "4") {
                break;
            }
            System.out.println("Please enter in your password:");
            String password = reader.nextLine();

            //either has a profile or profile is null;
            foundProfile = profileDao.getByUsername(username);

            if (foundProfile != null) {
                if (!foundProfile.getPassword().equals(password)) {
                    foundProfile = null;
                }
            }
        }

        //user manages to login, otherwise loop in above now let them decide what to do
        System.out.println("Welcome " + foundProfile.getFirstname() + " " + foundProfile.getLastname());
        System.out.println("Press:\n 1 to view accounts\n 2 to deposit money into an account\n 3 to withdraw money\n 4 to transfer\n 5 to apply for a new account\n -1 to logout");
        int choice = 0;
        while (true) {
            try {
                choice = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please select: 1 to view accounts, 2 to deposit money into an account, 3 to withdraw, 4 to transfer 5 to apply for a new account or -1 to logout");
                continue;
            }
            if (choice > -2 && choice < 6 && choice != 0) {
                break;
            }
        }
        while (choice != -1) {
            switch (choice) {
                case 1:
                    viewAccounts(foundProfile);
                    break;
                case 2:
                    depositMoney(foundProfile);
                    break;
                case 3:
                    withdrawMoney(foundProfile);
                    break;
                case 4:
                    transferMoney(foundProfile);
                    break;
                case 5:
                    applyForAcc(foundProfile);
                    break;
            }

            System.out.println("Please select: 1 to view accounts, 2 to deposit money into an account, 3 to withdraw, 4 to transfer 5 to apply for a new account or -1 to logout");
            while (true) {
                try {
                    choice = Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException ex) {
                    System.out.println("Please select: 1 to view accounts, 2 to deposit money into an account, 3 to withdraw, 4 to transfer 5 to apply for a new account or -1 to logout");
                    continue;
                }
                if (choice > -2 && choice < 6 && choice != 0) {
                    break;
                }
            }
        }
    }

    private void viewAccounts(Profile userProfile) {
        ArrayList<Integer> foundAccounts = accountDao.getAccountsOwned(userProfile.profileId);
        for (int i = 0; i < foundAccounts.size(); i++) {
            Account foundAccount = accountDao.getByID(foundAccounts.get(i));
            System.out.println("Account " + i + "'s status is: " + foundAccount.getStatus() + ". Current Balance is: $" + foundAccount.getBalance());
        }
    }

    private void depositMoney(Profile userProfile) {
        ArrayList<Integer> foundAccounts = accountDao.getAccountsOwned(userProfile.profileId);
        ArrayList<Account> storedAccounts = new ArrayList<>();
        for (int i = 0; i < foundAccounts.size(); i++) {
            Account foundAccount = accountDao.getByID(foundAccounts.get(i));
            System.out.println("Account " + i + "'s status is: " + foundAccount.getStatus() + ". Current Balance is: $" + foundAccount.getBalance());
            storedAccounts.add(foundAccount);
        }
        System.out.println("Pleases select an account between 0 and " + (foundAccounts.size() - 1) + "or press -1 to exit");
        Scanner reader = new Scanner(System.in);
        int choice = 0;

        while (true) {
            try {
                choice = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Pleases select a valid account between 0 and " + (foundAccounts.size() - 1));
            }
            if (choice > -2 && choice < foundAccounts.size()) {
                if(storedAccounts.get(choice).getStatus() != "pending" && storedAccounts.get(choice).getStatus() != "cancelled"){
                    break;
                }
                System.out.println("Pleases select a valid account between 0 and " + (foundAccounts.size() - 1));
            }
        }
        if(choice != -1){
            System.out.println("Please enter how much money you wish to deposit");
            double depositAmount = 0;
            //double currentBalance = accountDao.getBalance(foundAccounts.get(choice));
            double currentBalance = storedAccounts.get(choice).getBalance();
            while(true){
                try{
                    depositAmount = Double.parseDouble(reader.nextLine());
                } catch(NumberFormatException ex){
                    System.out.println("Please enter in a valid amount greater than 0");
                }
                if(depositAmount > 0){
                    break;
                }
                System.out.println("Please enter in a valid amount greater than 0");
            }
            //have the user input for account selection and deposit amount
            accountDao.setBalance(foundAccounts.get(choice), currentBalance + depositAmount);
        }
    }

    public void withdrawMoney(Profile userProfile) {
        ArrayList<Integer> foundAccounts = accountDao.getAccountsOwned(userProfile.profileId);
        ArrayList<Account> storedAccounts = new ArrayList<>();
        for (int i = 0; i < foundAccounts.size(); i++) {
            Account foundAccount = accountDao.getByID(foundAccounts.get(i));
            System.out.println("Account " + i + "'s status is: " + foundAccount.getStatus() + ". Current Balance is: $" + foundAccount.getBalance());
            storedAccounts.add(foundAccount);
        }
        System.out.println("Pleases select an account between 0 and " + (foundAccounts.size() - 1) + "or press -1 to exit");
        Scanner reader = new Scanner(System.in);
        int choice = 0;

        while (true) {
            try {
                choice = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Pleases select a valid account between 0 and " + (foundAccounts.size() - 1));
            }
            if (choice > -2 && choice < foundAccounts.size()) {
                if(storedAccounts.get(choice).getStatus() != "pending" && storedAccounts.get(choice).getStatus() != "cancelled"){
                    break;
                }
                System.out.println("Pleases select a valid account between 0 and " + (foundAccounts.size() - 1));
            }
        }
        if(choice != -1){
            System.out.println("Please enter how much money you wish to withdraw");
            double withdrawAmount = 0;
            //double currentBalance = accountDao.getBalance(foundAccounts.get(choice));
            double currentBalance = storedAccounts.get(choice).getBalance();
            while(true){
                try{
                    withdrawAmount = Double.parseDouble(reader.nextLine());
                } catch(NumberFormatException ex){
                    System.out.println("Please enter in a valid amount greater than 0 and less than " + currentBalance);
                }
                if(withdrawAmount >= 0 && withdrawAmount <= currentBalance){
                    break;
                }
                System.out.println("Please enter in a valid amount less than " + currentBalance);
            }
            //have the user input for account selection and deposit amount
            accountDao.setBalance(foundAccounts.get(choice), currentBalance - withdrawAmount);
        }
    }

    public void applyForAcc(Profile userProfile){
        Account newAccount = new Account();
        //TODO set account requestedtype
        accountDao.createAccount(userProfile, newAccount);
        System.out.println("You have successfully applied for a new account, please wait for employee approval");
    }
    //TODO transfer
    public void transferMoney(Profile userProfile){

    }

    public void accountRegistration(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter in your first name:");
        String firstname = reader.nextLine();
        while(!alphaCheck(firstname)){
            System.out.println("No numbers or special characters allowed in first name.\nPlease enter in your first name:");
            firstname = reader.nextLine();
        }


        System.out.println("Please enter in your last name:");
        String lastname = reader.nextLine();
        while(!alphaCheck(lastname)){
            System.out.println("No numbers or special characters allowed in last name.\nPlease enter in your last name:");
            lastname = reader.nextLine();
        }

        System.out.println("Please enter in your birthday(format mm/dd/yyyy):");
        String birthday = reader.nextLine();
        while(!birthdayCheck(birthday)){
            System.out.println("Please enter in a valid birthday using mm/dd/yyyy format");
            birthday = reader.nextLine();
        }

        //TODO check for duplicate usernames, reprompt if there is
        System.out.println("Please select a username (4 to 20 characters long, alphanumeric characters only):");
        String username = reader.nextLine();
        while(!alphaNumCheck(username) || !(username.length() > 3 && username.length() < 21)){
            System.out.println("Please use alphanumeric characters only and length between 4 and 20");
            username = reader.nextLine();
        }

        System.out.println("Please select a password (6 to 20 characters long):");
        String password = reader.nextLine();
        while(!(password.length() > 5 && password.length() < 21)){
            System.out.println("Please select a password between 6 and 20 characters long");
            password = reader.nextLine();
        }

        Profile newCustomer = new Profile(username, password, firstname, lastname, birthday);
        profileDao.newProfile(newCustomer);
        Account newAccount = new Account();

        accountDao.createAccount(newCustomer, newAccount);
        System.out.println("Thank you for applying for an account");

    }


    public boolean alphaCheck(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public boolean alphaNumCheck(String input){
        return input.matches("^[a-zA-Z0-9]*$");
    }

    public boolean birthdayCheck(String input){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        return sdf.parse(input, new ParsePosition(0)) != null;
    }
}
