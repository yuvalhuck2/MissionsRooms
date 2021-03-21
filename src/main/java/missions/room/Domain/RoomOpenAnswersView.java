package missions.room.Domain;

import java.util.List;

public interface RoomOpenAnswersView {
    String getRoomId();
    String getName();
    List<OpenAnswer> getOpenAnswers();
}
