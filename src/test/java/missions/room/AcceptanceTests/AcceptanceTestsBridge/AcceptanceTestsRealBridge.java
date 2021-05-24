package missions.room.AcceptanceTests.AcceptanceTestsBridge;

import DataObjects.APIObjects.NewRoomDetails;
import DataObjects.APIObjects.RoomTemplateDetailsData;
import DataObjects.APIObjects.RoomsDataByRoomType;
import DataObjects.FlatDataObjects.*;
import DataObjects.APIObjects.RegisterDetailsData;
import ExternalSystems.MailSender;
import ExternalSystems.VerificationCodeGenerator;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.*;
import missions.room.Service.ITService;
import missions.room.Service.RoomService;
import missions.room.Service.RoomTemplateService;
import missions.room.Service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcceptanceTestsRealBridge implements AcceptanceTestBridge{

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private ITService itService;

    @Autowired
    private RoomTemplateService roomTemplateService;

    @Autowired
    private RoomService roomService;

    private String teacherApiKey;

    private String templateId;

    @Override
    public Boolean register(RegisterDetailsTest details) {
        return userAuthenticationService.register(convertRegisterDetails(details)).getValue() != null;
    }

    @Override
    public Boolean registerCode(String alias, String code, String teacherAlias) {
        return userAuthenticationService.registerCode(alias, code, teacherAlias, GroupType.A).getValue();
    }

    @Override
    public String login(String alias, String password) {
        return userAuthenticationService.login(alias, password).getValue();
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
    public Boolean openRoom(RoomDetailsTest roomDetails) {
        return roomService.createRoom(teacherApiKey, convertRoomDetails(roomDetails)).getValue();
    }

    @Override
    public Boolean closeRoom(String token, String roomId) {
        return null;
    }

    @Override
    public Boolean addRoomTemplate(RoomTemplateDetailsTestData roomTemplateDetails) {
        boolean answer = roomTemplateService.createRoomTemplate(teacherApiKey,
                ConvertRoomTemplateDetails(roomTemplateDetails))
                .getValue();
        templateId = roomTemplateService.searchRoomTemplates(teacherApiKey).getValue().iterator().next().getId();
        return answer;
    }

    @Override
    public Boolean deducePoint(String token, String aliasOfStudent, int pointsToDeduce) {
        return null;
    }

    @Override
    public Boolean removeUser(String token, String alias) {
        return itService.deleteUser(token, alias).getValue();
    }

    @Override
    public Boolean uploadCsv(String token, byte[] csv) {
        return null;
    }

    @Override
    public void setExternalSystems(MailSender mailSender, VerificationCodeGenerator verificationCodeGenerator) {
        userAuthenticationService.setExternalSystems(mailSender, verificationCodeGenerator);
    }

    @Override
    public boolean registerCodeTeacher(String alias, String verificationCode) {
        return userAuthenticationService.registerCode(alias, verificationCode, alias, GroupType.C).getValue();
    }

    @Override
    public boolean teacherLogin(String alias, String password) {
        teacherApiKey = userAuthenticationService.login(alias, password).getValue();
        return teacherApiKey!= null;
    }

    @Override
    public RoomDetailsTest watchRoomDetails() {
        List<RoomDetailsData> roomDetailsDatList = roomService.watchMyClassroomRooms(teacherApiKey)
                .getValue()
                .get(0)

                .getRoomDetailsDataList();
        if(roomDetailsDatList.isEmpty())
            return null;
        return convertRoomDetailsToRoomDetailsTest(roomDetailsDatList.get(0));
    }

    private RoomDetailsTest convertRoomDetailsToRoomDetailsTest(RoomDetailsData roomDetailsData) {
        return new RoomDetailsTest(
                ConvertMissionDataToMissionTestData(roomDetailsData.getCurrentMission()),
                roomDetailsData.getName(),
                convertType(roomDetailsData.getRoomType()),
                roomDetailsData.getNumberOfMissions(),
                roomDetailsData.getCurrentMissionNumber());
    }

    private MissionDetailsTest ConvertMissionDataToMissionTestData(MissionData currentMission) {
        return new MissionDetailsTest(
                currentMission.getMissionId(),
                currentMission.getMissionTypes()
                        .stream()
                        .map(this::convertType).
                        collect(Collectors.toList()),
                currentMission.getStory()
        );
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

    private RoomTemplateDetailsData ConvertRoomTemplateDetails(RoomTemplateDetailsTestData roomTemplateDetails) {
        return new RoomTemplateDetailsData(roomTemplateDetails.getMissionIds(),
                roomTemplateDetails.getName(),
                roomTemplateDetails.getMinimalMissionsToPass(),
                convertType(roomTemplateDetails.getType()));
    }

    private RoomType convertType(ParticipantTypeTest type) {
        if(type == null)
            return null;
        switch (type){
            case Group:
                return RoomType.Group;
            case Class:
                return RoomType.Class;
            default:
                return RoomType.Personal;
        }
    }

    private ParticipantTypeTest convertType(RoomType type) {
        switch (type){
            case Group:
                return ParticipantTypeTest.Group;
            case Class:
                return ParticipantTypeTest.Class;
            default:
                return ParticipantTypeTest.Personal;
        }
    }

    private NewRoomDetails convertRoomDetails(RoomDetailsTest roomDetails) {
        return new NewRoomDetails(roomDetails.getParticipant(),
                convertType(roomDetails.getParticipantType()),
                roomDetails.getRoomName(),
                templateId,
                roomDetails.getBonus());
    }
}
