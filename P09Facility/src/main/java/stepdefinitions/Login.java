package stepdefinitions;

import Pages.LoginPage;
import io.cucumber.java.en.Given;

public class Login {


    @Given("user logged into P09 Facility")
    public void login(){
        System.out.println("System.out.println(\"******** USING FRAMEWORK CUCUMBER EXECUTOR ********\");");
        LoginPage loginPage = new LoginPage();
        loginPage.login();
    }
}
