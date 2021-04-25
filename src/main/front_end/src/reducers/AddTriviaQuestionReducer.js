const initialState = {
    apiKey: "",
    errorMessage: "",
    loading: false,
    question: "",
    wrongAnswers: [],
    correctAnswer: "",
    subject: ""
}

export default (state = initialState, action) => {
    switch(action.type){
        default:
            return state;
    }
}