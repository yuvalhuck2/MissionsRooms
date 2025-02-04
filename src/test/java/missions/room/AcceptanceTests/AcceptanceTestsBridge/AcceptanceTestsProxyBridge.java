package missions.room.AcceptanceTests.AcceptanceTestsBridge;

import ExternalSystems.MailSender;
import ExternalSystems.VerificationCodeGenerator;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcceptanceTestsProxyBridge implements AcceptanceTestBridge{

    @Autowired
    private AcceptanceTestsRealBridge realBridge;

    public AcceptanceTestsProxyBridge(){
        //this.realBridge = null;
    }

//    public void setRealBridge(AcceptanceTestBridge bridge){
//        this.realBridge = bridge;
//    }

    @Override
    public Boolean register(RegisterDetailsTest details) {
        if(realBridge!=null)
            return realBridge.register(details);
        return true;
    }

    @Override
    public Boolean registerCode(String alias, String code, String teacherRegisterDetailsAlias) {
        if(realBridge!=null)
            return realBridge.registerCode(alias, code, teacherRegisterDetailsAlias);
        return false;
    }

    @Override
    public String login(String alias, String password) {
        if(realBridge!=null)
            return realBridge.login(alias, password);
        return null;
    }

    @Override
    public Boolean resetPassword() {
        return null;
    }

    @Override
    public List<ScoreRecordTestData> getRecordTableByType(String token, ParticipantTypeTest type) {
        if(realBridge!=null)
            return realBridge.getRecordTableByType(token, type);
        return null;
    }

    @Override
    public Boolean changePassword(String token, String currentPassword, String newPassword) {
        if(realBridge!=null)
            return realBridge.changePassword(token, currentPassword, newPassword);
        return false;
    }

    @Override
    public RoomDetailsTest getRoomDetails(String token, String roomId) {
        if(realBridge!=null)
            return realBridge.getRoomDetails(token, roomId);
        return null;
    }

    @Override
    public Boolean answerOpenQuestion(String token, String roomId, String missionId, String answer, byte[] file) {
        if(realBridge!=null)
            return realBridge.answerOpenQuestion(token, roomId, missionId, answer, file);
        return false;
    }

    @Override
    public Boolean openRoom(RoomDetailsTest roomDetails) {
        if(realBridge!=null)
            return realBridge.openRoom(roomDetails);
        return false;
    }

    @Override
    public RoomDetailsTest watchRoomDetails() {
        if(realBridge!=null)
            return realBridge.watchRoomDetails();
        return null;
    }

    @Override
    public Boolean closeRoom(String token, String roomId) {
        if(realBridge!=null)
            return realBridge.closeRoom(token, roomId);
        return false;
    }

    @Override
    public Boolean addRoomTemplate(RoomTemplateDetailsTestData roomTemplateDetails) {
        if(realBridge!=null)
            return realBridge.addRoomTemplate(roomTemplateDetails);
        return false;
    }

    @Override
    public Boolean deducePoint(String token, String aliasOfStudent, int pointsToDeduce) {
        if(realBridge!=null)
            return realBridge.deducePoint(token, aliasOfStudent, pointsToDeduce);
        return false;
    }

    @Override
    public Boolean removeUser(String token, String alias) {
        if(realBridge!=null)
            return realBridge.removeUser(token, alias);
        return false;
    }

    @Override
    public Boolean uploadCsv(String token, byte[] csv) {
        if(realBridge!=null)
            return realBridge.uploadCsv(token, csv);
        return false;
    }

    @Override
    public void setExternalSystems(MailSender mailSender, VerificationCodeGenerator verificationCodeGenerator) {
        if(realBridge!=null)
             realBridge.setExternalSystems(mailSender, verificationCodeGenerator);
    }

    @Override
    public boolean registerCodeTeacher(String alias, String verificationCode) {
        if(realBridge != null)
            return realBridge.registerCodeTeacher(alias, verificationCode);
        return true;
    }

    @Override
    public boolean teacherLogin(String alias, String password) {
        if(realBridge != null)
            return realBridge.teacherLogin(alias, password);
        return true;
    }
}
