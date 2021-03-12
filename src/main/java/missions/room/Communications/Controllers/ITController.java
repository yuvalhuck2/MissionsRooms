package missions.room.Communications.Controllers;

import DataAPI.AddITData;
import DataAPI.Response;
import missions.room.Service.ITService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // This means that this class is a Controller
@RequestMapping(path="/it")
public class ITController extends AbsController{

    @Autowired
    private ITService itService;

    @PostMapping("/add")
    public Response<?> addNewIT(@RequestBody String itDetailsDataStr){
        AddITData itDetailsData= json.fromJson(itDetailsDataStr, AddITData.class);
        return itService.addNewIT(itDetailsData);
    }
}
