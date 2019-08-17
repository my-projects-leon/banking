package banking;

import jdk.nashorn.internal.lookup.Lookup;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Driver {
    private static int mode;

    //static Driver_Admin adminMode = new Driver_Admin();
    static Driver_Customers userMode = new Driver_Customers();
    static Driver_Employee employeeMode = new Driver_Employee();
    static Driver_Admin adminMode = new Driver_Admin();

    static private HashMap<String, String> employeeLoginData = new HashMap<>();

    public static void choices(){
        employeeLoginData.put("admin", "admin");
        employeeLoginData.put("employee", "employee");


        System.out.println("Welcome to Revature Banking App, please enter\n 1 if you are a customer\n 2 if you are an employee\n 3 if you are an admin\n 4 if you wish to exit");
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter in a number:");
        while(true){
            try{
                mode = Integer.parseInt(reader.nextLine());
            }catch(NumberFormatException ex){
                System.out.println("Please select an option between 1 and 4:");
                continue;
            }
            if(mode == 1 || mode == 2 || mode == 3 || mode == 4){
                break;
            }
        }

        while(mode != 4){
            //branch to user mode
            if(mode == 1){
                userMode.customerStart();
            }
            //employee mode
            else if(mode == 2){
                employeeMode.employeeStart();
            }
            //admin mode
            else if(mode == 3){
                adminMode.adminStart();
            }
            //next choice
            System.out.println("Welcome to Revature Banking App, please enter\n (1) If you are a customer\n (2) If you are an employee\n (3) If you are an admin\n (4) If you wish to exit");
            while(true){
                try{
                    mode = Integer.parseInt(reader.nextLine());
                }catch(NumberFormatException ex){
                    System.out.println("Please select an option between 1 and 4:");
                    continue;
                }
                if(mode == 1 || mode == 2 || mode == 3 || mode == 4){
                    break;
                }
            }
        }
        System.out.println("Thank you for using Revature banking");
    }

    public static HashMap<String, String> getEmployeeLoginData() {
        return employeeLoginData;
    }
}
