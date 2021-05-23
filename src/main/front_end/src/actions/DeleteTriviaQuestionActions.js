export const questionChanged = (question) => {
    return {
        type: DELETE_TRIVIA_QUESTION_CHANGED,
        payload: question,
    }
}

export const deleteTriviaQuestion = ({ apiKey, questionId, navigation }) => {
    return async (dispatch) => {
        try {
          const res = await API.post(
            `${APIPaths.getTriviaQuestions}?apiKey=${apiKey}`, {triviaQuestionStr: questionId} 
          );
          res
            ? checkGetDeleteQuestionResponse(res.data, dispatch, navigation)
            : dispatch({ type: UPDATE_DELETE_TRIVIA_QUESTION_ERROR, payload: server_error});
        } catch (err) {
          return dispatch({ type: "" });
        }
      };
    };
    
    const checkGetDeleteQuestionResponse = (data, dispatch, navigation) => {
      const { reason, value } = data;
      switch (reason) {
        case Success:
          return dispatch({ type: GOT_TRIVIA_QUESTIONS, payload: value });
        default:
          return dispatch({ type: "" });
      }
    };