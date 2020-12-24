package missions.room.LoginManagerTests;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class LoginManagerTestsRealUserRepo extends LoginManagerTestsAllStubs {



    @BeforeEach
    void SetUp() {
        super.setUp();
    }

    @Override
    void setUpMocks(){
    }
}


