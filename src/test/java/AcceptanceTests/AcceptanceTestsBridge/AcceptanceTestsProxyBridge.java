package AcceptanceTests.AcceptanceTestsBridge;

import AcceptanceTests.AcceptanceTestDataObjects.*;

import java.util.List;

public class AcceptanceTestsProxyBridge implements AcceptanceTestBridge{
    private AcceptanceTestBridge realBridge;

    public AcceptanceTestsProxyBridge(){
        this.realBridge = null;
    }

    public void setRealBridge(AcceptanceTestBridge bridge){
        this.realBridge = bridge;
    }

    @Override
    public Boolean register(RegisterDetailsTest details) {
        if(realBridge!=null)
            return realBridge.register(details);
        return false;
    }

    @Override
    public Boolean registerCode(String alias, String code) {
        if(realBridge!=null)
            return realBridge.registerCode(alias, code);
        return false;
    }

    @Override
    public String login(String alias, String hashedPassword) {
        if(realBridge!=null)
            return realBridge.login(alias, hashedPassword);
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
    public Boolean openRoom(String token, RoomDetailsTest roomDetails) {
        if(realBridge!=null)
            return realBridge.openRoom(token, roomDetails);
        return false;
    }

    @Override
    public Boolean closeRoom(String token, String roomId) {
        if(realBridge!=null)
            return realBridge.closeRoom(token, roomId);
        return false;
    }

    @Override
    public Boolean addRoomTemplate(String token, RoomTemplateDetailsTestData roomTemplateDetails) {
        if(realBridge!=null)
            return realBridge.addRoomTemplate(token, roomTemplateDetails);
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
}
