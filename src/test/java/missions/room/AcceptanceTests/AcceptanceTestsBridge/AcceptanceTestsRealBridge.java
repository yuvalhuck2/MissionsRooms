package missions.room.AcceptanceTests.AcceptanceTestsBridge;

import DataAPI.RegisterDetailsData;
import DataAPI.UserType;
import ExternalSystems.MailSender;
import ExternalSystems.VerificationCodeGenerator;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.*;
import missions.room.Service.LoginService;
import missions.room.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcceptanceTestsRealBridge implements AcceptanceTestBridge{

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

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
    public String login(String alias, String password) {
        return loginService.login(alias, password).getValue();
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

    @Override
    public void setExternalSystems(MailSender mailSender, VerificationCodeGenerator verificationCodeGenerator) {
        registerService.setExternalSystems(mailSender, verificationCodeGenerator);
    }

    private RegisterDetailsData convertRegisterDetails(RegisterDetailsTest registerDetailsTestData){
        RegisterDetailsData registerDetailsData = new RegisterDetailsData(registerDetailsTestData.getAlias(), registerDetailsTestData.getHashedPassword());
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
