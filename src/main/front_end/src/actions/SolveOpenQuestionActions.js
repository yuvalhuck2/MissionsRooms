import { uploadStringsErrors, openQuestionErrors, uploadStrings, GeneralErrors, SolveOpenQuestionMissionErrors } from '../locale/locale_heb';
import * as DocumentPicker from 'expo-document-picker';
import { baseURL } from '../api/API';
import { Platform } from 'react-native';
import {
    CURRENT_ANSWER_CHANGED,
    FINISH_MISSION,
    SEND_OPEN_ANSWER,
    PICKED_FILE,
    UPDATE_ERROR,
    RESET_FILES
} from './types';

import {
    INVALID_ROOM_ID,
    Success,
  } from './OpCodeTypes';
const { success } = uploadStrings;
const { mission_in_charge } = openQuestionErrors;
import { parseUploadCsvResponse } from '../actions/ITActions'



const {
    empty_answer
} = SolveOpenQuestionMissionErrors;

const {
    server_error,
} = GeneralErrors

const {
    server_not_responding
} = uploadStringsErrors;

export const answerChanged = (text) => {
    return {
        type: CURRENT_ANSWER_CHANGED,
        payload: text,
    };
}

export const onPickFile = () => {
    return async (dispatch) => {
        DocumentPicker.getDocumentAsync({
            type: '*/*',
            multiple: true,
            copyToCacheDirectory: true,
        })
            .then((res) => {
                if (res != undefined && res != null && res.type != 'cancel') {
                    dispatch({ type: PICKED_FILE, payload: res });
                }
            })
            .catch((err) => alert('Document Picker Error'));
    };
};

export const sendOpenQuestionAnswer = ({ roomId, missionId, file, apiKey, currentAnswer }) => {
    return async (dispatch) => {
        if ((file == null || file == undefined) && (currentAnswer == null || currentAnswer.trim() == "")) {
            dispatch({ type: UPDATE_ERROR, payload: empty_answer });
        } else {
            try {
                sendOpenAnswerData(file, roomId, missionId, currentAnswer, apiKey, dispatch);
            } catch (err) {
                console.log(err);
                return dispatch({ type: UPDATE_ERROR, payload: server_error });
            }
        }
    }
};

export const sendOpenAnswerData = (file, roomId, missionId, openAnswer, apiKey, dispatch) => {
    const formData = new FormData();
    if (file != null && file != undefined) {
        formData.append('file', {
            uri:
                Platform.OS === 'android'
                    ? file.uri
                    : file.uri.replace('file://', ''),
            type: '*/*',
            name: file.name,
        });
    }


    formData.append("roomId", roomId);
    formData.append("missionId", missionId);
    formData.append("openAnswer", openAnswer);

    const xhr = new XMLHttpRequest();
    // 2. open request
    let uri = baseURL + '/student/room/openAns?token=' + apiKey;
    xhr.open('POST', uri);
    //Send the proper header information along with the request
    xhr.setRequestHeader('Content-type', 'multipart/form-data');
    // 3. set up callback for request
    xhr.onload = () => {
        const response = JSON.parse(xhr.responseText);
        parseOpenQuestionResponse(response, dispatch, apiKey);
    };
    // 4. catch for request error
    xhr.onerror = (e) => {
        dispatch({ type: UPDATE_ERROR, payload: server_error });
    };
    // 4. catch for request timeout
    xhr.ontimeout = (e) => {
        dispatch({ type: UPDATE_ERROR, payload: server_not_responding });
    };

    xhr.onreadystatechange = function () {//Call a function when the state changes.
        if (this.readyState === 4) {
            const response = JSON.parse(xhr.responseText);
            parseOpenQuestionResponse(response, dispatch, apiKey);
        }
    }
    dispatch({ type: SEND_OPEN_ANSWER, payload: '' });
    xhr.send(formData);
    dispatch({ type: RESET_FILES });
};

const parseOpenQuestionResponse = (data, dispatch, apiKey) => {
    const { reason, value } = data;
    switch (reason) {
      case Success:
        return dispatch({ type: FINISH_MISSION, payload: apiKey });
      case INVALID_ROOM_ID:
        return dispatch({ type: UPDATE_ERROR, payload: mission_in_charge });
      default:
        parseUploadCsvResponse(data, dispatch);
    }
  };

