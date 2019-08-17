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
            if (username.equals("4")) {
                return;
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
            System.out.println("Account #" + foundAccounts.get(i) + "'s status is: " + foundAccount.getStatus() + ". Current Balance is: $" + foundAccount.getBalance());
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
                continue;
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
                    continue;
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
                continue;
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
                    continue;
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
        System.out.println("Which type of account would you like to apply to?\n 1 Single\n 2 Joint");
        Scanner reader = new Scanner(System.in);
        int choice = 0;
        while (true) {
            try {
                choice = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Not Valid Choice! Please select: 1 for Single 2 for Joint or -1 to exit");
                continue;
            }
            if (choice > -2 && choice < 3 && choice != 0) {
                break;
            }
        }
        if(choice == 1)
        {
            System.out.println("You have successfully applied for a Single account! Please wait for employee approval.");
            accountDao.createAccount(userProfile, newAccount);
        }else{
            System.out.print("You have choosen to apply for a joint account. Is this an:\n 1 Existing Account\n" +
                    " 2 New Account\nPlease enter choice(or -1 to exit):");
            while (true) {
                try {
                    choice = Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException ex) {
                    System.out.println("Not Valid Choice! Please select: 1 for Existing Joint account 2 for a New Joint Account or -1 to exit");
                    continue;
                }
                if(choice == -1){
                    return;
                }
                if (choice > -2 && choice < 3 && choice != 0) {
                    break;
                }
            }
            if(choice == 1){
                System.out.println("Please Enter the Account # of the Joint Account you would like to join(or -1 to exit): ");
                while (true) {
                    try {
                        choice = Integer.parseInt(reader.nextLine());
                    } catch (NumberFormatException ex) {
                        System.out.println("Not Valid Choice! Please select a valid Joint account number or -1 to exit ");
                        continue;
                    }
                    if(choice == -1){
                        return;
                    }
                    Account temp = accountDao.getByID(choice);
                    if (temp != null && temp.requestedType.equals("joint")) {
                        System.out.println("Success! Joint Account #" + choice + "has been applied to.\n" +
                                " Account has been set to pending. Please wait for employee approval.");
                        accountDao.newAccountsOwned(userProfile.profileId,choice);
                        accountDao.setStatus(choice,"pending");
                        break;
                    }
                }

            }else{
                newAccount.setRequestedType("Joint");
                System.out.println("You have successfully applied for a New Joint account! Please wait for employee approval.");
                accountDao.createAccount(userProfile, newAccount);
            }
        }

    }

    public void transferMoney(Profile userProfile){
        Scanner reader = new Scanner(System.in);

        ArrayList<Integer> foundAccounts = accountDao.getAccountsOwned(userProfile.profileId);
        ArrayList<Account> storedAccounts = new ArrayList<>();
        for (int i = 0; i < foundAccounts.size(); i++) {
            Account foundAccount = accountDao.getByID(foundAccounts.get(i));
            System.out.println("Account " + i + "'s status is: " + foundAccount.getStatus() + ". Current Balance is: $" + foundAccount.getBalance());
            storedAccounts.add(foundAccount);
        }
        System.out.println("Pleases select an account between 0 and " + (foundAccounts.size() - 1) + " which you wish to transfer money out of or press -1 to exit");
        int choice = 0;

        while (true) {
            try {
                choice = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Pleases select a valid account between 0 and " + (foundAccounts.size() - 1));
                continue;
            }
            if(choice == -1){
                return;
            }
            else if (choice > -1 && choice < foundAccounts.size()) {
                if(!storedAccounts.get(choice).getStatus().equals("pending") && storedAccounts.get(choice).getStatus().equals("cancelled")){
                    break;
                }
                System.out.println("Pleases select a valid account between 0 and " + (foundAccounts.size() - 1));
            }
        }

        System.out.println("Please enter in how much money you wish to move from this account");
        double transferAmount = 0;
        while(true){
            try{
                transferAmount = Double.parseDouble(reader.nextLine());
            }catch(NumberFormatException ex){
                System.out.println("Please enter in a valid number");
                continue;
            }
            if(transferAmount >= 0 && transferAmount <= storedAccounts.get(choice).getBalance()){
                break;
            }
            System.out.println("Please enter in a non-negative amount less than or equal to " + storedAccounts.get(choice).getBalance());
        }

        System.out.println("Next please enter in the account number that you wish to transfer $" + transferAmount + " into or -1 to exit");
        int secondAccountNum = 0;
        Account secondAccount;
        while (true) {
            try {
                secondAccountNum = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter in a valid account number");
                continue;
            }
            if(secondAccountNum == -1){
                return;
            }

            secondAccount = accountDao.getByID(secondAccountNum);

            if (secondAccount != null && !secondAccount.getStatus().equals("pending") && !secondAccount.getStatus().equals("cancelled")) {
                break;
            } else {
                System.out.println("Please enter in a valid account number or -1 to cancel this transaction");
            }
        }

        accountDao.transferMoney(foundAccounts.get(choice), (storedAccounts.get(choice).getBalance() - transferAmount), secondAccountNum, (secondAccount.getBalance() + transferAmount));
        System.out.println("$"+ transferAmount + " has been succesfully transfered.\n Account " + secondAccountNum + "'s new balance is $" + (secondAccount.getBalance() + transferAmount));

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

        System.out.println("Please select a username (4 to 20 characters long, alphanumeric characters only, its case sensitive):");
        String username = reader.nextLine();
        do {

            while (!alphaNumCheck(username) || !(username.length() > 3 && username.length() < 21)) {
                System.out.println("Please use alphanumeric characters only and length between 4 and 20");
                username = reader.nextLine();
            }
            if(profileDao.getByUsername(username) == null){
                break;
            }
            username = "a";
            System.out.println("This username is taken! Please insert a different username.");
        }while (true);

        System.out.println("Please select a password (6 to 20 characters long):");
        String password = reader.nextLine();
        while(!(password.length() > 5 && password.length() < 21)){
            System.out.println("Please select a password between 6 and 20 characters long");
            password = reader.nextLine();
        }

        Profile newCustomer = new Profile(username, password, firstname, lastname, birthday);
        profileDao.newProfile(newCustomer);
        newCustomer = profileDao.getByUsername(username);
        Account newAccount = new Account();

        accountDao.createAccount(newCustomer, newAccount);
        System.out.println("Thank you for creating a Profile.\n" +
                " A default account of type: Single, has been created for you! It is now in pending status.");

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
