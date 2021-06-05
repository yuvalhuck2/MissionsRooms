package missions.room.Communications.Controllers;

import DataObjects.FlatDataObjects.Response;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbsController {

    protected Gson json ;

    public AbsController() {
        this.json = new Gson();
    }

}
