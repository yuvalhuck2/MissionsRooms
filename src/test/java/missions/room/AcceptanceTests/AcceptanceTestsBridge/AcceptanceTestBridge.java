package missions.room.AcceptanceTests.AcceptanceTestsBridge;


import ExternalSystems.MailSender;
import ExternalSystems.VerificationCodeGenerator;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.*;

import java.util.List;

public interface AcceptanceTestBridge {

    //Guest 2.2 - Register - step 1
    Boolean register(RegisterDetailsTest details);

    //Guest 2.2 - Register  - step 2
    Boolean registerCode(String alias, String code, String teacherRegisterDetailsAlias);

    //Guest 2.3 - Login
    String login(String alias, String hashedPassword);

    //Guest 3.8
    Boolean resetPassword();

    //Student 3.1 - records table
    List<ScoreRecordTestData> getRecordTableByType(String token, ParticipantTypeTest type);

    //Student 3.3 - Change Password
    Boolean changePassword(String token, String currentPassword, String newPassword);

    //Student 3.6.1 - Enter Room
    RoomDetailsTest getRoomDetails(String token, String roomId);

    //Student 3.6.2.3
    Boolean answerOpenQuestion(String token, String roomId, String missionId, String answer, byte[] file);

    //Teacher 4.1 - Create new room
    Boolean openRoom(RoomDetailsTest roomDetails);

    //Teacher 4.2 - Close existing room
    Boolean closeRoom(String token, String roomId);

    //Teacher 4.4 - Add room template
    Boolean addRoomTemplate(RoomTemplateDetailsTestData roomTemplateDetails);

    //Teacher 4.13 - Deduce point
    Boolean deducePoint(String token, String aliasOfStudent, int pointsToDeduce);

    //IT 6.2 - remove user
    Boolean removeUser(String token, String alias);

    //IT 6.3 - upload CSV
    Boolean uploadCsv(String token, byte[] csv);

    RoomDetailsTest watchRoomDetails();

    void setExternalSystems(MailSender mailSender, VerificationCodeGenerator verificationCodeGenerator);


    boolean registerCodeTeacher(String alias, String verificationCode);

    boolean teacherLogin(String alias, String password);
}
