package banking.dao;

import banking.Account;
import banking.Profile;

import java.util.ArrayList;
import java.util.List;

public interface AccountDao {
    //create a new account
    public void createAccount(Profile user, Account account);
    //the the info of an account by passing id
    public Account getByID(int id);
    //used to check if status is active if not account can not be used
    public String getStatus(int id);
    //used to set status from active, pending, or inactive
    public void setStatus(int id, String status);
    //used to check if an account has the balance required to withdraw
    //public Boolean checkBalance(int id);
    //withdraw money from an account and return the new balance
    public double getBalance(int id);
    //deposit money into and account and return the new balance
    public void setBalance(int id, double amount);
    //add another account to accounts owned table
    public void newAccountsOwned(int profId, int accountId);
    //retrieve the account numbers of all the accounts owned by a profile
    public ArrayList<Integer> getAccountsOwned(int profId);
    //retrieve all pending accounts
    public ArrayList<Integer> getPendingAccounts();

}
