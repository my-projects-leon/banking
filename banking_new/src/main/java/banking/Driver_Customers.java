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
}
