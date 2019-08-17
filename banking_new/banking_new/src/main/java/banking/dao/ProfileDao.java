package banking.dao;

import banking.Profile;

public interface ProfileDao {

    //check to see if username is available
    public boolean profileExist(String username);
    //recover frofile with username to check password or other details
    public  Profile getByUsername(String username);
    //after check if username is available create new profile
    public void newProfile(Profile profile);

    public void updateAccountsOwned(int profId, int accountId);


}
