import { INIT_OPEN_ANSWER } from "./types";
import * as NavPaths from '../navigation/NavPaths';

export const handleBack = ({navigation}) =>{
    navigation.goBack()
}

export const onMissionPress = ({ navigation, roomId, solutionData, apiKey }) => {
    return async (dispatch) => {
        dispatch({ type: INIT_OPEN_ANSWER, payload: { apiKey, roomId, solutionData} });
        navigation.navigate(NavPaths.WatchOpenAnswerSolutionScreen);
    }

}