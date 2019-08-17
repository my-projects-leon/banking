package banking;

import java.io.Serializable;
import java.util.ArrayList;


public class Account implements Serializable {
    private double balance;
    private String status;
    public String requestedType;
    //private ArrayList<Integer> owners;

    public Account(){
        //owners = new ArrayList<>();
        this.balance = 0;
        this.status = "pending";
        this.requestedType = "single";
        //this.owners.add(id);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestedType() {
        return requestedType;
    }

    public void setRequestedType(String requestedType) {
        this.requestedType = requestedType;
    }
}

