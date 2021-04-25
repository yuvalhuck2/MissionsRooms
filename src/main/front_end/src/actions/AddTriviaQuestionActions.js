import API from "../api/API"

export const addQuestion = ({apiKey, question}) => {
    return async (dispatch) => {
        try{
            const res = await API.post(`${APIPaths.addTriviaSubject}?apiKey=${apiKey}`,{question})
            res 
                ? checkAddTriviaQuestionResponse(res.data, dispatch)
                : dispatch({type: ADD_TRIVIA_QUESTION_ERROR, payload: server_error})
        }catch(err){
            return dispatch({
                type: ADD_TRIVIA_QUESTION_ERROR, 
                payload: server_error
            })
        }
    }
}

const checkAddTriviaQuestionResponse = (data, dispatch) => {

}
