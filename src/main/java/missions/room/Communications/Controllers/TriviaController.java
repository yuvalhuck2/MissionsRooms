package missions.room.Communications.Controllers;

import DataObjects.APIObjects.TriviaQuestionData;
import DataObjects.APIObjects.TriviaSubjectData;
import DataObjects.FlatDataObjects.Response;
import missions.room.Service.TriviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//can be moved later to TeacherController
@RestController
public class TriviaController extends AbsController {

    @Autowired
    private TriviaService triviaService;

    @PostMapping("/addTriviaSubject")
    public Response<Boolean> addTriviaSubject(@RequestBody String subjectName, @RequestParam String apiKey){
        TriviaSubjectData triviaSubjectData = json.fromJson(subjectName, TriviaSubjectData.class);
        return triviaService.createTriviaSubject(apiKey, triviaSubjectData.getSubject());
    }

    @PostMapping("/addTriviaQuestion")
    public Response<Boolean> addTriviaQuestion(@RequestBody String triviaQuestionStr, @RequestParam String apiKey){
        TriviaQuestionData triviaQuestionData = json.fromJson(triviaQuestionStr, TriviaQuestionData.class);
        return triviaService.addTriviaQuestion(apiKey, triviaQuestionData);
    }

    @PostMapping("/deleteTriviaQuestion")
    public Response<Boolean> deleteTriviaQuestion(@RequestBody String triviaQuestionStr, @RequestParam String apiKey){
        TriviaQuestionData triviaQuestionData = json.fromJson(triviaQuestionStr, TriviaQuestionData.class);
        return triviaService.deleteTriviaQuestion(apiKey, triviaQuestionData.getId());
    }

    @PostMapping("/getTriviaQuestions")
    public Response<?> getTriviaQuestions(@RequestParam String apiKey){
        return triviaService.getTriviaQuestions(apiKey);
    }

    @PostMapping("/getTriviaSubjects")
    public Response<?> getTriviaSubjects(@RequestParam String apiKey){
        return triviaService.getTriviaSubjects(apiKey);
    }

}
