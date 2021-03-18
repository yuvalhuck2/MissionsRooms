import API from "../api/API";
import * as APIPaths from "../api/APIPaths";
import { ChooseRoomStudentErrors, GeneralErrors } from "../locale/locale_heb";
import * as NavPaths from "../navigation/NavPaths";
import {
  IN_CHARGE,
  NOT_BELONGS_TO_ROOM,
  Not_Exist,
  Not_Exist_Room,
  NOT_IN_CHARGE,
  Wrong_Key,
} from "./OpCodeTypes";
import {
  CURRENT_ROOM_CHANGED,
  DETERMINISTIC_NAME,
  EXIT_ROOM,
  INIT_DETEREMINISTIC,
  INIT_STORY_MISSION,
  PASS_TO_SOLVE_MISSIONS,
  STORY_NAME,
  TRIVIA_NAME,
  UPDATE_ERROR_CHOOSE_ROOM,
  WAIT_FOR_ROOM_DATA,
} from "./types";

const { room_empty } = ChooseRoomStudentErrors;

const {
  server_error,
  wrong_key_error,
  student_not_exist,
  room_error,
} = GeneralErrors;

export const roomChanged = (room) => {
  return {
    type: CURRENT_ROOM_CHANGED,
    payload: room.roomId,
  };
};

export const passToSolveMission = ({ currentRoom, navigation, apiKey }) => {
  return async (dispatch) => {
    if (currentRoom == undefined) {
      dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: room_empty });
    } else {
      try {
        dispatch({ type: WAIT_FOR_ROOM_DATA });
        const res = await API.post(APIPaths.watchRoomData, {
          apiKey,
          roomId: currentRoom.roomId,
        });
        res
          ? handleRoomData(res.data, dispatch, navigation)
          : dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: server_error });
      } catch (err) {
        console.log(err);
        return dispatch({
          type: UPDATE_ERROR_CHOOSE_ROOM,
          payload: server_error,
        });
      }
    }
  };
};

const handleRoomData = (data, dispatch, navigation) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({
        type: UPDATE_ERROR_CHOOSE_ROOM,
        payload: wrong_key_error,
      });
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_CHOOSE_ROOM,
        payload: student_not_exist,
      });
    case NOT_BELONGS_TO_ROOM:
      return dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: room_error });
    case Not_Exist_Room:
      return dispatch({ type: UPDATE_ERROR_CHOOSE_ROOM, payload: room_error });
    case IN_CHARGE:
      return moveToMission(value, dispatch, navigation, true);
    case NOT_IN_CHARGE:
      return moveToMission(value, dispatch, navigation, false);
  }
  alert("didn't handle room");
};

const getTriviaQuestionsMap = (triviaMission) => {
  let numOfQuestions = triviaMission.questions.length;
  let questionsArray = [];
  for (var i = 0; i < numOfQuestions; i++) {
    let id = `id${i}`;
    let question = {
      qustionId: id,
      question: triviaMission.questions[i],
      correctAnswer: triviaMission.answers[i],
      answers: triviaMission.possibleAnswers[i],
      currentAnswer: triviaMission.answers[0],
    };
    questionsArray.push([id, question]);
  }
  return new Map(questionsArray);
};

export const moveToMission = (roomData, dispatch, navigation, isInCharge) => {
  switch (roomData.currentMission.name) {
    case DETERMINISTIC_NAME:
      dispatch({ type: PASS_TO_SOLVE_MISSIONS });
      dispatch({
        type: INIT_DETEREMINISTIC,
        payload: { roomData, isInCharge },
      });
      navigation.navigate(NavPaths.deterministicScreen);
      break;
    case STORY_NAME:
      dispatch({ type: PASS_TO_SOLVE_MISSIONS });
      dispatch({ type: INIT_STORY_MISSION, payload: { roomData, isInCharge } });
      navigation.navigate(NavPaths.storyScreen);
      break;
    case TRIVIA_NAME:
      dispatch({ type: PASS_TO_SOLVE_MISSIONS });
      let currentMission = roomData.currentMission;
      let triviaMap = getTriviaQuestionsMap(currentMission);
      dispatch({
        type: INIT_TRIVIA_MISSION,
        payload: { currentMission, triviaMap, isInCharge },
      });
      break;
    default:
      console.log(roomData);
      alert("didn't move from room");
  }
};

export const handleBack = ({ navigation, apiKey, roomId, missionId }) => {
  return async (dispatch) => {
    try {
      API.post(APIPaths.disconnectFromRoom, { apiKey, roomId });
      navigation.navigate(NavPaths.chooseStudentRoom);
      dispatch({ type: EXIT_ROOM, payload: missionId });
    } catch (err) {
      console.log(err);
      alert(err);
    }
  };
};
