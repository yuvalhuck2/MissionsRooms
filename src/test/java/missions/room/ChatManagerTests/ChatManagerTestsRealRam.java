package missions.room.ChatManagerTests;

import missions.room.Domain.Ram;
import missions.room.Managers.ChatManager;
import missions.room.Managers.TeacherManager;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static Data.DataConstants.NULL_USER_KEY;
import static Data.DataConstants.WRONG_USER_NAME;
import static org.junit.jupiter.api.Assertions.fail;

public class ChatManagerTestsRealRam extends ChatManagerTestsAllStubs{

    private Ram realRam;

    @Override
    protected void initRam(){
        realRam=new Ram();
        try {
            Field managerRam = ChatManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            managerRam.set(chatManager,realRam);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        realRam.addApi(studentApiKey
                ,student.getAlias());
        realRam.addAlias(studentApiKey);
        realRam.addApi(teacherApiKey,
                teacher.getAlias());
        realRam.addAlias(teacherApiKey);
        realRam.addApi(NULL_USER_KEY
                , WRONG_USER_NAME);
    }

    @Test
    void enterChatHappyCase() {
        realRam.addRoom(room);
        super.enterChatHappyCase();
    }

    @Override
    protected void tearDownMocks() {
        realRam.clearRam();
        super.tearDownMocks();
    }
}
