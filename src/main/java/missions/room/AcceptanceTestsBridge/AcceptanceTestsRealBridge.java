package missions.room.AcceptanceTestsBridge;

import DataAPI.RegisterDetailsData;
import DataAPI.UserType;
import missions.room.Service.RegisterService;
import missions.room.AcceptanceTestDataObjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcceptanceTestsRealBridge implements AcceptanceTestBridge{

    @Autowired
    private RegisterService registerService;

    public AcceptanceTestsRealBridge(){
        //this.registerService = new RegisterService();
    }

    @Override
    public Boolean register(RegisterDetailsTest details) {
        return registerService.register(convertRegisterDetails(details)).getValue();
    }

    @Override
    public Boolean registerCode(String alias, String code) {
        return registerService.registerCode(alias, code).getValue();
    }

    @Override
    public String login(String alias, String hashedPassword) {
        return null;
    }

    @Override
    public Boolean resetPassword() {
        return null;
    }

    @Override
    public List<ScoreRecordTestData> getRecordTableByType(String token, ParticipantTypeTest type) {
        return null;
    }

    @Override
    public Boolean changePassword(String token, String currentPassword, String newPassword) {
        return null;
    }

    @Override
    public RoomDetailsTest getRoomDetails(String token, String roomId) {
        return null;
    }

    @Override
    public Boolean answerOpenQuestion(String token, String roomId, String missionId, String answer, byte[] file) {
        return null;
    }

    @Override
    public Boolean openRoom(String token, RoomDetailsTest roomDetails) {
        return null;
    }

    @Override
    public Boolean closeRoom(String token, String roomId) {
        return null;
    }

    @Override
    public Boolean addRoomTemplate(String token, RoomTemplateDetailsTestData roomTemplateDetails) {
        return null;
    }

    @Override
    public Boolean deducePoint(String token, String aliasOfStudent, int pointsToDeduce) {
        return null;
    }

    @Override
    public Boolean removeUser(String token, String alias) {
        return null;
    }

    @Override
    public Boolean uploadCsv(String token, byte[] csv) {
        return null;
    }

    private RegisterDetailsData convertRegisterDetails(RegisterDetailsTest registerDetailsTestData){
        UserType type = getUserType(registerDetailsTestData.getType());
        RegisterDetailsData registerDetailsData = new RegisterDetailsData(
                registerDetailsTestData.getfName(), registerDetailsTestData.getlName(),
                registerDetailsTestData.getAlias(), registerDetailsTestData.getHashedPassword(),
                type, registerDetailsTestData.getClassRoom());
        return registerDetailsData;
    }

    private UserType getUserType(UserTypeTest type) {
        switch(type){
            case Teacher:
                return UserType.Teacher;
            case IT:
                return UserType.IT;
            case Supervisor:
                return UserType.Supervisor;
            default:
                return UserType.Student;

        }
    }
}
