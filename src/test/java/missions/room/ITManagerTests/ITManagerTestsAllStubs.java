package missions.room.ITManagerTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import ExternalSystems.HashSystem;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.IT;
import missions.room.Managers.ITManager;
import missions.room.Repo.ITRepo;
import missions.room.Repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ITManagerTestsAllStubs {

    @InjectMocks
    protected ITManager itManager;

    @Mock
    protected Ram mockRam;

    @Mock
    protected UserRepo mockUserRepo;

    @Mock
    protected ITRepo mockITRepo;

    protected DataGenerator dataGenerator;

    protected String ITApiKey;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        ITApiKey=IT_API_KEY;
        initMocks();
    }

    private void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        IT it = (IT) dataGenerator.getUser(Data.VALID_IT);
        String itAlias = it.getAlias();

        IT it2 = (IT) dataGenerator.getUser(Data.VALID_IT2);
        String itAlias2 = it2.getAlias();

        initRam(itAlias);
        initUserRepo(it, itAlias2);
        initITRepo(it, itAlias, it2);
        initHashSystem();
    }

    private void initHashSystem() {
        try {
            Field hashSystem = ITManager.class.getDeclaredField("hashSystem");
            hashSystem.setAccessible(true);
            hashSystem.set(itManager, new HashSystem());

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    protected void initITRepo(IT it, String itAlias, IT it2) {
        when(mockITRepo.save(any(IT.class)))
                .thenReturn(new Response<>(it2, OpCode.Success));
        when(mockITRepo.findITById(itAlias))
                .thenReturn(new Response<>(it,OpCode.Success));
        when(mockITRepo.findITById(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
    }

    protected void initUserRepo(IT it, String itAlias2) {
        when(mockUserRepo.isExistsById(itAlias2))
                .thenReturn(new Response<>(false, OpCode.Success));
        when(mockUserRepo.isExistsById(it.getAlias()))
                .thenReturn(new Response<>(true, OpCode.Success));
    }

    protected void initRam(String itAlias) {
        when(mockRam.getAlias(IT_API_KEY))
                .thenReturn(itAlias);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_USER_KEY))
                .thenReturn(WRONG_USER_NAME);
    }

    @Test
    void addNewITHappyCase(){
        Response<Boolean> response = itManager.addNewIT(ITApiKey,dataGenerator.getRegisterDetails(Data.VALID_IT2));
        assertTrue(response.getValue());
        assertEquals(response.getReason(),OpCode.Success);
    }

    @Test
    void addNewITEmptyAlias(){
        addNewITInvalid(OpCode.Wrong_Alias,dataGenerator.getRegisterDetails(Data.EMPTY_ALIAS));
    }

    @Test
    void addNewITNullAlias(){
        addNewITInvalid(OpCode.Wrong_Alias,dataGenerator.getRegisterDetails(Data.NULL_ALIAS));
    }

    @Test
    void addNewITEmptyPassword(){
        addNewITInvalid(OpCode.Wrong_Password,dataGenerator.getRegisterDetails(Data.EMPTY_PASSWORD));
    }

    @Test
    void addNewITNullPassword(){
        addNewITInvalid(OpCode.Wrong_Password,dataGenerator.getRegisterDetails(Data.NULL_PASSWORD));
    }

    @Test
    void addNewITWrongKey(){
        ITApiKey = INVALID_KEY;
        addNewITInvalid(OpCode.Wrong_Key,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    void addNewITNotExistAlias(){
        ITApiKey = NULL_USER_KEY;
        addNewITInvalid(OpCode.Not_Exist,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    void addNewITITRepoFindByIdThrowsException(){
        when(mockITRepo.findITById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        addNewITInvalid(OpCode.DB_Error,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    void addNewITUserRepoIsExistsByIdThrowsException(){
        when(mockUserRepo.isExistsById(anyString()))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        addNewITInvalid(OpCode.DB_Error,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    @Test
    void addNewITAlreadyExistAlias(){
        addNewITInvalid(OpCode.Already_Exist,dataGenerator.getRegisterDetails(Data.VALID_IT));
    }

    @Test
    void addNewITITRepoSaveThrowsException(){
        when(mockITRepo.save(any(IT.class)))
                .thenReturn(new Response<>(null,OpCode.DB_Error));
        addNewITInvalid(OpCode.DB_Error,dataGenerator.getRegisterDetails(Data.VALID_IT2));
    }

    protected void addNewITInvalid(OpCode opCode, RegisterDetailsData registerDetailsData){
        Response<Boolean> response = itManager.addNewIT(ITApiKey,registerDetailsData);
        assertFalse(response.getValue());
        assertEquals(response.getReason(),opCode);
    }

    @AfterEach
    void tearDown() {
        tearDownMocks();
    }

    protected void tearDownMocks(){
        try {
            closeable.close();
        } catch (Exception e) {
            fail("close mocks when don't need to");
        }
    }
}