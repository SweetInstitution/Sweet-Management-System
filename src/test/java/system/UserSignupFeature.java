package system;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class UserSignupFeature {
    SweetSystem myApp;
    User user;
    public UserSignupFeature(SweetSystem myApp) {
        this.myApp = myApp;
    }

    @Given("user is not registered in the Sweet System")
    public void userIsNotRegisteredInTheSweetSystem() {
        assertFalse(myApp.isRegisteredIn());
    }

    @When("user enters a username {string} and a password {string} and an email {string} and his city {string}")
    public void userEntersAUsernameAndAPasswordAndAnEmailAndHisCity(String username, String password,String email, String city) {
        user = new User(username, password);
        user.setEmail(email);
        user.setCity(city); //used those methods to include them into out testing.


        if (myApp.isValidUsername(username) && myApp.isValidPassword(password) && !myApp.isUserRegistered(username, password)) {
            myApp.setUserValid(true);
            assertTrue(myApp.isUserValid());
        } else {
            myApp.setUserValid(false);
            myApp.setMessage("Invalid Credentials!");
            assertFalse(myApp.isUserValid());
        }
        assertTrue(myApp.isValidPassword("password"));
        assertFalse(myApp.isValidUsername(""));
    }

    @Then("user is registered into the Sweet System")
    public void userIsRegisteredIntoTheSweetSystem() {
        boolean test = myApp.isUserRegistered(user.getUsername(), user.getPassword());
        assertFalse(test);
    }

    @Then("a successful signup message will appear")
    public void aSuccessfulSignupMessageWillAppear() {
        boolean messageAppeared = true;
        String userDetailsShouldExist = user.viewAccountDetails();
        assertNotNull(userDetailsShouldExist);
        Boolean userShouldHaveAShoppingCartExists = User.loadOrdersFromFile("DumbFile.txt");
        assertNotNull(userShouldHaveAShoppingCartExists);
        assertTrue(messageAppeared);
    }


    @Then("wrong credential message will appear")
    public void wrongCredentialMessageWillAppear() {
        String expectedMessage = "Invalid Credentials!";
        assertEquals("Invalid credentials message not displayed correctly", expectedMessage, myApp.getMessage());
    }
}





