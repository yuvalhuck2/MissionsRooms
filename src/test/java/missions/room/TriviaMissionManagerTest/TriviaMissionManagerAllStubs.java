package missions.room.TriviaMissionManagerTest;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import CrudRepositories.TriviaQuestionRepository;
import CrudRepositories.TriviaSubjectRepository;
import com.google.gson.Gson;
import missions.room.Domain.Ram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriviaMissionManagerAllStubs {

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Autowired
    protected MissionCrudRepository missionCrudRepository;

    @Autowired
    protected TriviaSubjectRepository triviaSubjectRepository;

    @Autowired
    protected TriviaQuestionRepository triviaQuestionRepository;

    protected Ram ram;

    protected String missionString;

    protected Gson gson;

    protected String apiKey;

}
