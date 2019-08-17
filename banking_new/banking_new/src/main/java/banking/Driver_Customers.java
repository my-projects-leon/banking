package banking;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import banking.services.services;
import org.apache.log4j.Logger;
public class Driver_Customers {

    private int initMode;
    final static Logger logger = Logger.getLogger(Driver_Customers.class);

    services servicer = new services();
    public void customerStart(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Welcome valued customer! Enter in:\n 1 to login\n 2 to register for an account\n 3 to return to the start menu");
        while(true){
            try{
                this.initMode = Integer.parseInt(reader.nextLine());
            }catch(NumberFormatException ex){
                System.out.println("Please choose either 1 to login, 2 to register for an account or 3 to return to the start menu:");
                continue;
            }
            if(this.initMode == 1 || this.initMode == 2 || this.initMode == 3){
                break;
            }
        }
        while(this.initMode != 3){
            if(this.initMode == 1){
                servicer.userLogin();
            }else if(this.initMode == 2){
                servicer.accountRegistration();
            }
            //TODO check this logic
            System.out.println("Please choose either 1 to login, 2 to register for an account or 3 to return to the start menu:");
            //this.initMode = reader.nextInt();

            while(true){
                try{
                    this.initMode = Integer.parseInt(reader.nextLine());
                }catch(NumberFormatException ex){
                    System.out.println("Please choose either 1 to login, 2 to register for an account or 3 to return to the start menu:");
                    continue;
                }
                if(this.initMode == 1 || this.initMode == 2 || this.initMode == 3){
                    break;
                }
            }
        }
    }



    public void transferMoney(int customerId){
        System.out.println("Please first select an account you would like to withdraw from.");
//        double amount = withdrawTrans(user);
//        ArrayList<Account> allAccounts = Driver.getAllAccounts();
        Scanner reader = new Scanner(System.in);

        System.out.println("Please enter the account which you wish to transfer to");

        int accountSelection;
        while(true){
            try{
                accountSelection = Integer.parseInt(reader.nextLine());
            }catch(NumberFormatException ex){
                System.out.println("Please enter the account which you wish to modify or -1 to return to menu");
                continue;
            }
            //if(accountSelection < allAccounts.size() - 1 && accountSelection > -2) {
            if(true){
                break;
            }
        }
//        Account targetAcc = allAccounts.get(accountSelection);
//        targetAcc.addBalance(amount);
//        System.out.println("Account #" + accountSelection + "'s new balance is: $" + targetAcc.getBalance());
    }


//    public void applyForAcc(int customerId){
//        System.out.println("What type of account do you wish to apply for:\n 1 Single account\n 2 Joint account\n 3 Return to menu");
//        Scanner reader = new Scanner(System.in);
//
//        int accType = 0;
//        while(true){
//            try{
//                accType = Integer.parseInt(reader.nextLine());
//            }catch(NumberFormatException ex){
//                System.out.println("Please pick either 1 for single account, 2 for joint acc or 3 to return to the menu");
//            }
//            if(accType == 1 || accType == 2 || accType == 3){
//                break;
//            }
//        }
//
//        //TODO replace this with database mod
//        Account newAccount = new Account(customerId);
//
//
//        switch(accType) {
//            case 1:
//                newAccount.requestedType = "single";
//                //Driver.addAllAccounts(newAccount);
////                Driver.getAllAccounts().add(newAccount);
////                Driver.getPendingAccounts().add(newAccount);
//            break;
//
//            case 2:
//                newAccount.requestedType = "joint";
//                //Driver.addAllAccounts(newAccount);
////                Driver.getAllAccounts().add(newAccount);
////                Driver.getPendingAccounts().add(newAccount);
//                break;
//
//            case 3:
//                //TODO back to menu option
//                customerActions(customerId);
//                break;
//        }
//
//    }

}
