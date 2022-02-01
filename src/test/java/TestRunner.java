import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestRunner {
    Customer customer;
    @org.testng.annotations.Test(priority = 0)
    public void DoLogIn() throws ConfigurationException, IOException {
         customer=new Customer();
        customer.CallingCustomerLoginInApi();

    }

    @org.testng.annotations.Test(priority = 1)
    public void getCustomerList() throws ConfigurationException, IOException {
        customer=new Customer();

        customer.callingCustomerListApi();
    }

    @org.testng.annotations.Test(priority = 2)
    public void SearchCustomer() throws ConfigurationException, IOException {
        customer=new Customer();

        customer.callingSearchApi();
    }

    @org.testng.annotations.Test(priority = 3)
    public void GenerateNewCustomer() throws ConfigurationException, IOException {
        customer=new Customer();

        customer.GenerateCustomer();
    }
    @org.testng.annotations.Test(priority = 4)
    public void CreateNewCustomer() throws ConfigurationException, IOException {
        customer=new Customer();
        customer.CreateCustomer();
    }
    @org.testng.annotations.Test(priority = 5)
    public void UpdateNCustomer() throws ConfigurationException, IOException {
        customer=new Customer();
        customer.UpdateCustomer();
    }
    @Test(priority = 6)
    public void DeleteCustomer() throws ConfigurationException, IOException {
        customer=new Customer();
        customer.DeletedCustomer();
    }
}
