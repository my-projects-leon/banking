package banking;

import banking.services.employeeServices;

import java.util.HashMap;
import java.util.Scanner;

public class Driver_Admin {

    public void adminStart(){
        Scanner reader = new Scanner(System.in);

        System.out.println("Welcome Admin of Revature Bank, please enter in your username");
        String username = reader.nextLine();
        System.out.println("Please enter in your password");
        String password = reader.nextLine();

        boolean loginStatus = employeeLogin(username, password);
        //loop until successful login or exit
        while(!loginStatus){
            System.out.println("Login failed, please enter in your username");
            username = reader.nextLine();
            System.out.println("Please enter in your password");
            password = reader.nextLine();
            loginStatus = employeeLogin(username, password);
        }

        System.out.println("Login successful\n please select an action:\n 1 View customer information\n 2 approve accounts\n 3 Moodify Accounts\n 4 Exit");

        int choice = 0;
        while(true) {
            try {
                choice = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Not valid choice! please select an action:\n 1 View customer information\n 2 approve accounts\n 3 Moodify Accounts\n 4 Exit");
                continue;
            }
            if(choice == 1 || choice == 2 || choice == 3|| choice == 4) {
                break;
            }
        }
        employeeServices eServices = new employeeServices();
        while(choice != 4) {
            if (choice == 1) {
                Profile customer = eServices.customerLookup();
                eServices.viewAccount(customer);
            } else if (choice == 2) {
                eServices.approveAccounts();
            }else if(choice == 3){
                eServices.modifyAccounts();
            }
            System.out.println("Please select either 1 to look up a customer, 2 to approve accounts, 3 to modify accounts or 4 to exit ");
            while(true) {
                try {
                    choice = Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException ex) {
                    System.out.println("Please select either 1 to look up a customer, 2 to approve accounts, 3 to modify accounts or 4 to exit");
                    continue;
                }
                if(choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                    break;
                }
            }
        }
    }
    //TODO change to addmin login credentials
    private boolean employeeLogin(String username, String password){
        HashMap<String, String> employees = Driver.getEmployeeLoginData();
        //TODO check for null return
        String savedPass = employees.get(username);
        while(savedPass == null){
            //loop
            savedPass = employees.get("test");
        }

        if(savedPass.equals(password)){
            return true;
        }else{
            return false;
        }
    }
}
