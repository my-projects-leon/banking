package banking;

import banking.services.employeeServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver_Employee {

    public void employeeStart(){
        Scanner reader = new Scanner(System.in);

        System.out.println("Welcome employee of Revature Bank, please enter in your username");
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

        System.out.println("Login successful\n please select an action:\n 1 customer lookup\n 2 approve accounts\n 3 exit");

        int choice = 0;
        while(true) {
            try {
                choice = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please pick either 1 to lookup a customer, 2 to approve accounts or 3 to exit");
                continue;
            }
            if(choice == 1 || choice == 2 || choice == 3) {
                break;
            }
        }
        employeeServices eServices = new employeeServices();
        while(choice != 3) {
            if (choice == 1) {
                Profile customer = eServices.customerLookup();
                eServices.viewAccount(customer);
            } else if (choice == 2) {
                eServices.approveAccounts();
            }
            System.out.println("Please select either 1 to look up a customer, 2 to approve accounts or 3 to exit");
            while(true) {
                try {
                    choice = Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException ex) {
                    System.out.println("Please pick either 1 to lookup a customer, 2 to approve accounts or 3 to exit");
                    continue;
                }
                if(choice == 1 || choice == 2 || choice == 3) {
                    break;
                }
            }
        }
    }

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
