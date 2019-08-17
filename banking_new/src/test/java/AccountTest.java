import org.junit.Test;

import static org.junit.Assert.*;
/*
public class AccountTest {

    @Test
    public void setBalance() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Account temp = new Account(testProfile);
        temp.setBalance(500);
        double balance = temp.getBalance();
        assert(balance == 500);
    }

    @Test
    public void addBalance() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Account temp = new Account(testProfile);
        temp.addBalance(500);
        double balance = temp.getBalance();
        assert(balance == 500);
    }

    @Test
    public void newOwner() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Customer testProfile2 = new Customer("d","e","f", "22/22/2222");
        Account temp = new Account(testProfile);
        int originialOwnerNum = temp.getOwners().size();
        temp.newOwner(testProfile2);
        assert(temp.getOwners().size() == originialOwnerNum + 1);

    }

    @Test
    public void getOwners() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Account temp = new Account(testProfile);

        Profile foundProfile = temp.getOwners().get(0);
        assert(foundProfile.getFirstName() == testProfile.getFirstName());
    }

    @Test
    public void setRequestedType() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Account temp = new Account(testProfile);
        temp.setRequestedType("single");
        assert(temp.getRequestedType() == "single");
    }

    @Test
    public void getRequestedType() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Account temp = new Account(testProfile);
        temp.setRequestedType("single");
        String found = temp.getRequestedType();
        assert(found == "single");
    }

    @Test
    public void setStatus() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Account temp = new Account(testProfile);
        temp.setStatus("single");
        assert(temp.getStatus() == "single");
    }

    @Test
    public void getStatus() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Account temp = new Account(testProfile);
        assert(temp.getStatus() == "pending");
    }

    @Test
    public void getBalance() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Account temp = new Account(testProfile);
        assert(temp.getBalance() == 0);

    }

    @Test
    public void deleteOwner() {
        Customer testProfile = new Customer("a", "b", "c", "11/11/1111");
        Customer testProfile2 = new Customer("d","e","f", "22/22/2222");
        Account temp = new Account(testProfile);
        temp.newOwner(testProfile2);
        temp.deleteOwner(testProfile);
        assert(temp.getOwners().get(0) == testProfile2);
    }
}
*/