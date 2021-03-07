package missions.room.Communications.Controllers;

import DataAPI.Response;
import missions.room.Service.TriviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//can be moved later to TeacherController
@RestController
public class TriviaController {

    @Autowired
    private TriviaService triviaService;

    @PostMapping("/addTriviaSubject")
    public Response<Boolean> addTriviaSubject(@RequestBody String subjectName, @RequestParam String token){
        return triviaService.createTriviaSubject(token, subjectName);
    }

}
