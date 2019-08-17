package banking.services;

import banking.Account;
import banking.Profile;
import banking.dao.AccountDao;
import banking.dao.AccountDaoJdbc;
import banking.dao.ProfileDao;
import banking.dao.ProfileDaoJdbc;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

public class employeeServices {

    AccountDao accountDao = new AccountDaoJdbc();
    ProfileDao profileDao = new ProfileDaoJdbc();

    public void approveAccounts(){
        ArrayList<Integer> pendingAccounts = accountDao.getPendingAccounts();
        Scanner reader = new Scanner(System.in);

        for(int i = 0; i < pendingAccounts.size(); i++){
            int accId = pendingAccounts.get(i);
            System.out.println("Would you like to approve account #" + accId);
            System.out.println("Press 1 to approve or 2 to deny");
            int choice = 0;
            while(true){
                try{
                    choice = reader.nextInt();
                }catch(NumberFormatException ex){
                    System.out.println("Press 1 to approve or 2 to deny");
                    continue;
                }
                if(choice == 1 || choice == 2){
                    break;
                }
                System.out.println("Press 1 to approve or 2 to deny");
            }
            if(choice == 1){
                accountDao.setStatus(accId, "approved");
            }else{
                accountDao.setStatus(accId, "denied");
            }
        }
    }

    public Profile customerLookup(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter in customer username of account you want to view");
        String username = reader.nextLine();
        Profile profile = profileDao.getByUsername(username);
        while(profile == null)
        {
            System.out.println("profile does not exist. Please enter in customer username of account you want to view");
            username = reader.nextLine();
            profile = profileDao.getByUsername(username);
            if(profile != null)
                break;
        }

        return profile;
    }

    public void viewAccount(Profile customer){
        Scanner reader = new Scanner(System.in);
        System.out.println("Press:\n 1 to view account information\n 2 to view account balances\n 3 to view personal information\n 4 to exit");
        //int choice = reader.nextInt();
        int choice = 0;
        while(true) {
            try {
                choice = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter either 1 to view account information, 2 to veiw account balances, 3 to view personal information or 4 to exit");
                continue;
            }
            if(choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                break;
            }
        }
        ArrayList<Integer> accountsOwned = accountDao.getAccountsOwned(customer.profileId);

        while(choice != 4){
            if(choice == 1){
                System.out.println("Customer " + customer.getFirstname() + " " + customer.getLastname() + " has " + accountsOwned.size() + " accounts.");
                for(int i = 0; i < accountsOwned.size();i++){
                    System.out.println("Account " + accountsOwned.get(i) + " is a " + accountDao.getStatus(accountsOwned.get(i)) + " account");
                }
            }else if(choice == 2){
                System.out.println("Account balances are: ");
                for(int i = 0; i < accountsOwned.size();i++){
                    System.out.println("Account " + accountsOwned.get(i) + "'s current balance is $" + accountDao.getBalance(accountsOwned.get(i)));
                }
            }else if(choice == 3){
                System.out.println("First name: " + customer.getFirstname());
                System.out.println("Last name: " + customer.getLastname());
                System.out.println("Birthday: " + customer.getBirthday());
            }
            System.out.println("Select either: 1 to view account information, 2 to view account balances, 3 to view personal information or 4 to exit");
            while(true) {
                try {
                    choice = Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter either 1 to view account information, 2 to veiw account balances, 3 to view personal information or 4 to exit");
                    continue;
                }
                if(choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                    break;
                }
            }
        }
    }

    public void modifyAccounts() {
        System.out.println("Please enter in the account number of the account you wish to modify");
        Scanner reader = new Scanner(System.in);
        int accountNumber = 0;
        Account foundAccount = null;
        while (true) {
            try {
                accountNumber = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter in a valid account number");
                continue;
            }
            foundAccount = accountDao.getByID(accountNumber);
            if (foundAccount != null) {
                break;
            } else {
                System.out.println("Please enter in a valid account number");
            }
        }
        System.out.println("Press:\n 1 to deposit money into the account\n 2 to withdraw money from the account\n 3 to transfer to a different account\n" +
                " 4 to cancel the account\n or -1 to exit");
        int choice = 0;
        while (true) {
            try {
                choice = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please select: 1 to deposit money, 2 to withdraw money, 3 to transfer money, 4 to cancel this account or -1 to exit");
                continue;
            }
            if (choice > -2 && choice < 5 && choice != 0) {
                break;
            }
        }
        while (choice != -1) {
            switch (choice) {
                case 1:
                    double depositAmount = 0;
                    while (true) {
                        try {
                            depositAmount = Double.parseDouble(reader.nextLine());
                        } catch (NumberFormatException ex) {
                            System.out.println("Please enter in a number");
                            continue;
                        }
                        if (depositAmount > 0) {
                            break;
                        }
                        System.out.println("Please enter in a non-negative number");
                    }
                    accountDao.setBalance(accountNumber, (foundAccount.getBalance() + depositAmount));
                    System.out.println("Account " + accountNumber + "'s new balance is " + (foundAccount.getBalance() + depositAmount));
                    break;
                case 2:
                    double withdrawAmount = 0;
                    while (true) {
                        try {
                            withdrawAmount = Double.parseDouble(reader.nextLine());
                        } catch (NumberFormatException ex) {
                            System.out.println("Please enter in a number");
                            continue;
                        }
                        if (withdrawAmount > 0 && withdrawAmount <= foundAccount.getBalance()) {
                            break;
                        }
                        System.out.println("Please enter in a non-negative number less than or equal to " + foundAccount.getBalance());
                    }
                    accountDao.setBalance(accountNumber, (foundAccount.getBalance() - withdrawAmount));
                    System.out.println("Account " + accountNumber + "'s new balance is " + (foundAccount.getBalance() - withdrawAmount));
                    break;
                case 3:
                    transferMoney(foundAccount, accountNumber);
                    break;
                case 4:
                    accountDao.setStatus(accountNumber, "cancelled");
                    System.out.println("Account " + accountNumber + " has been cancelled");
                    break;
            }
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

    public void transferMoney(Account firstAccount, int accountNumber){
        Scanner reader = new Scanner(System.in);
        System.out.println("Account " + accountNumber +"'s current balance is $" + firstAccount.getBalance());
        System.out.println("Please enter in how much money you wish to move from this account");
        double transferAmount = 0;
        while(true){
            try{
                transferAmount = Double.parseDouble(reader.nextLine());
            }catch(NumberFormatException ex){
                System.out.println("Please enter in a valid number");
                continue;
            }
            if(transferAmount >= 0 && transferAmount <= firstAccount.getBalance()){
                break;
            }
            System.out.println("Please enter in a non-negative amount less than or equal to " + firstAccount.getBalance());
        }

        System.out.println("Next please enter in the account number that you wish to transfer $" + transferAmount + " into");
        int secondAccountNum = 0;
        Account secondAccount;
        while (true) {
            try {
                secondAccountNum = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter in a valid account number");
                continue;
            }
             secondAccount = accountDao.getByID(secondAccountNum);
            if (secondAccount != null) {
                break;
            } else {
                System.out.println("Please enter in a valid account number");
            }
        }
        accountDao.transferMoney(accountNumber, (firstAccount.getBalance() - transferAmount), secondAccountNum, (secondAccount.getBalance() + transferAmount));
        System.out.println("$"+ transferAmount + " has been succesfully transfered.\n Account " + secondAccountNum + "'s new balance is $" + (secondAccount.getBalance() + transferAmount));
    }
}
