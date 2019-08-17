package banking.dao;

import banking.Account;
import banking.Profile;
import banking.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoJdbc implements AccountDao {

    private static ConnectionUtil connectionUtil = ConnectionUtil.getConnectionUtil();
    @Override
    public void createAccount(Profile user, Account account) {
        try(Connection con = connectionUtil.getConnection() ){

            String query = "INSERT INTO account (status, request_type, balance) " +
                    "VALUES ( ?, ?, ?) RETURNING account_id";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, account.getStatus());
            ps.setString(2, account.getRequestedType());
            ps.setDouble(3, account.getBalance());

            ResultSet result = ps.executeQuery();
            if(result.next()) {
                int newId = result.getInt("account_id");
                System.out.println("the new account id is " + newId + " for profile" + user.profileId);
                query = "INSERT INTO accountsowned (account_id, profile_id) " + "VALUES (?, ?)";
                PreparedStatement ps2 = con.prepareStatement(query);
                ps2.setInt(1, newId);
                ps2.setInt(2, user.profileId);

                ps2.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getByID(int id) {
        try(Connection con = connectionUtil.getConnection() ){

            String query = "SELECT status, request_type, balance FROM account WHERE account_id = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1,id);

            ResultSet results = ps.executeQuery();

            Account account = new Account();
            if(results.next()){
                account.setStatus(results.getString("status"));
                account.setRequestedType(results.getString("request_type"));
                account.setBalance(results.getDouble("balance"));
            }

           return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getStatus(int id){
        try(Connection con = connectionUtil.getConnection() ){

            String query = "SELECT status FROM account WHERE account_id = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1,id);

            ResultSet results = ps.executeQuery();
            String status = null;
            if(results.next())
            {
                status = results.getString("status");
            }

            return status;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setStatus(int id, String status) {
        try(Connection con = connectionUtil.getConnection() ){

            String query = "UPDATE account SET status = ? WHERE account_id = ? ";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1,status);
            ps.setInt(2,id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public Boolean checkBalance(int id) {
//        try(Connection con = connectionUtil.getConnection() ){
//
//            String query = "";
//            PreparedStatement ps = con.prepareStatement(query);
//
//            ps.setInt(1,id);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public double getBalance(int id) {
        try(Connection con = connectionUtil.getConnection() ){

            String query = "SELECT balance FROM account WHERE id = ? ";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1,id);

            ResultSet result = ps.executeQuery();
            Double balance = 0.0;
            if(result.next()){
                balance = result.getDouble("balance");

            }

            return balance;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void setBalance(int id, double amount) {
        try(Connection con = connectionUtil.getConnection() ){

            String query = "UPDATE account SET balance = ? WHERE account_id = ? ";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1,amount);
            ps.setInt(2,id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newAccountsOwned(int profId, int accountId) {
        try(Connection con = connectionUtil.getConnection() ){

            String query = "INSERT INTO accountsOwned (account_id, profile_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1,accountId);
            ps.setInt(2,profId);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Integer> getAccountsOwned(int profId) {
        try(Connection con = connectionUtil.getConnection() ){

            String query = "SELECT account_id FROM accountsOwned WHERE profile_id = ?";
            //String query = "SELECT account_id FROM accountsOwned WHERE profile_id = " + profId;
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1,profId);

            ArrayList<Integer> accountsOwned = new ArrayList<>();
            ResultSet result = ps.executeQuery();
            while(result.next()){
                int account = result.getInt("account_id");

                accountsOwned.add(account);
            }

            return accountsOwned;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> getPendingAccounts(){
        ArrayList<Integer> pendingAccountNumbers = new ArrayList<>();
        try(Connection con = connectionUtil.getConnection() ){

            String query = "SELECT account_id FROM account WHERE status = 'pending'";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                int account = result.getInt("account_id");
                pendingAccountNumbers.add(account);
            }

            return pendingAccountNumbers;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void transferMoney(int firstId, double firstAccBalance, int secondId, double secondAccBalance){
        try(Connection con = connectionUtil.getConnection()){
            con.setAutoCommit(false);
            String query = "Update account SET balance = ? WHERE account_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDouble(1, firstAccBalance);
            ps.setInt(2, firstId);

            PreparedStatement ps2 = con.prepareStatement(query);
            ps2.setDouble(1, secondAccBalance);
            ps2.setInt(2, secondId);

            ps.execute();
            ps2.execute();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*catch(Exception ex){
        conn.rollback();
        }
         */
    }
}
