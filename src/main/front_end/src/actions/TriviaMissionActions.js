import {CHANGE_ANSWER_TRIVIA_MISSION} from './types'

export const answerChanged = (questionId, answer) =>{
    return {type: CHANGE_ANSWER_TRIVIA_MISSION, payload: {questionId, answer}}
}