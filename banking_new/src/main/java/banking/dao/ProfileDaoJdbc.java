package banking.dao;

import banking.Profile;
import banking.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDaoJdbc implements ProfileDao {
    private static ConnectionUtil connectionUtil = ConnectionUtil.getConnectionUtil();
    //not sure if needed.... get by username below does the same thing.... if an account is returned SUCCESS if not well not there.... right
    @Override
    public boolean profileExist(String username) {

        return false;
    }

    @Override
    public  Profile getByUsername(String username) {
        try(Connection con = connectionUtil.getConnection() ){

            String query = "SELECT profile_id, first_name, last_name,birthday, password FROM profile WHERE username = ? ";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1,username);

            ResultSet result = ps.executeQuery();

            boolean empty = true;
            while(result.next()) {
                int id = result.getInt("profile_id");
                String fname = result.getString("first_name");
                String lname = result.getString("last_name");
                String birthday = result.getString("birthday");
                String password = result.getString("password");

                Profile profile = new Profile(username, password, fname, lname, birthday);
                profile.profileId = id;
                return profile;
            }
            if(empty){
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void newProfile(Profile profile) {
        try(Connection con = connectionUtil.getConnection() ){

            String query = "INSERT INTO profile ( first_name, last_name, birthday, username, password) VALUES ( ?, ?, ?, ?, ?) ";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1,profile.getFirstname());
            ps.setString(2,profile.getLastname());
            ps.setString(3,profile.getBirthday());
            ps.setString(4,profile.getUsername());
            ps.setString(5,profile.getPassword());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccountsOwned(int profId, int accountId) {

    }
}
