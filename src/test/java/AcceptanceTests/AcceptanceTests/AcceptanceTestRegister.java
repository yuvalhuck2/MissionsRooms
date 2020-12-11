package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.RegisterDetailsTest;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTypeTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * use case 2.2 - Register
 */
public class AcceptanceTestRegister extends AcceptanceTest{
    private RegisterDetailsTest registerDetailsTest;
    @Before
    public void setup(){
        setUpCSV();
        UserTestData user = AcceptanceTest.students.get(1);
        this.registerDetailsTest = new RegisterDetailsTest(
                    user.getfName(), user.getlName(), "111", user.getAlias(), UserTypeTest.Student, "A");
    }

    @Test
    public void testRegisterSuccess(){
        boolean isRegistered = register(registerDetailsTest);
        assertTrue(isRegistered);
    }

    @Test
    public void testRegisterFailAlreadyRegistered(){
        testRegisterSuccess();
        boolean isRegistered = register(registerDetailsTest);
        assertFalse(isRegistered);
    }

    @Test
    public void testRegisterFailRegisterAsTeacher(){

    }

    @After
    public void tearDown(){

    }



}
