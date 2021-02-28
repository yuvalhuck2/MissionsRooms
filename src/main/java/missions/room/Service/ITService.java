package missions.room.Service;

import DataAPI.AddITData;
import DataAPI.RegisterDetailsData;
import DataAPI.Response;
import missions.room.Managers.ITManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ITService {

    @Qualifier("ITManager")
    @Autowired
    private ITManager itManager;

    /**
     * req 6.4 - adding new IT to the system
     * @param newIT - new IT details
     * @return if user added successfully
     */
    public Response<Boolean> addNewIT(AddITData newIT){
        return itManager.addNewIT(newIT.getApiKey(),
                new RegisterDetailsData(newIT.getAlias(),
                        newIT.getPassword()));
    }
}
