const initialState = {
    currentMission:{loading:false},
    currentRoom:undefined,
    // rooms:[
    //     {
    //         type:'Personal',
    //         roomId:'p1',
    //         name:'החדר האישי',
    //         currentMission:{
    //             missionId:"pmis1",
    //             name:"Known answer mission",
    //             question:['מה השם שלי '],
    //             answer:'יובל'
    //         },
    //     },
    //     {
    //         "key":5,
    //         type:'Group',
    //         roomId:'g2',
    //         name:'החדר הקבוצתי',
    //         currentMission:{
    //             missionId:"gmis2",
    //             name:"Known answer mission",
    //             question:['שאלה קבוצתית'],
    //             answer:'תשובה'
    //         },
    //     },
    //         {
    //         "key":3,
    //         type:'Class',
    //         roomId:'c3',
    //         name:'החדר הכיתתי',
    //         currentMission:{
    //             missionId:"cmis3",
    //             name:"Known answer mission",
    //             question:['שאלה כיתתית'],
    //             answer:'תשובה',
    //         },
    //     },
    // ],
    rooms:new Map([
        {
            type:'Personal',
            roomId:'p1',
            name:'החדר האישי',
            currentMission:{
                missionId:"pmis1",
                name:"Known answer mission",
                question:['מה השם שלי '],
                answer:'יובל'
            },
        },
        {
            type:'Group',
            roomId:'g2',
            name:'החדר הקבוצתי',
            currentMission:{
                missionId:"gmis2",
                name:"Known answer mission",
                question:['שאלה קבוצתית'],
                answer:'תשובה'
            },
        },
            {
            type:'Class',
            roomId:'c3',
            name:'החדר הכיתתי',
            currentMission:{
                missionId:"cmis3",
                name:"Known answer mission",
                question:['שאלה כיתתית'],
                answer:'תשובה',
            },
        },
    ].map((x)=>[x.roomId,x])),
    errorMessage: '',
  };

  import {
    SOLVE_MISSION,
    CURRENT_ROOM_CHANGED,
    UPDATE_ERROR_SOLVE_ROOM,
    CLEAR_STATE,
    PASS_TO_SOLVE_MISSIONS,
    CURRENT_ANSWER_CHANGED,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case CURRENT_ROOM_CHANGED:
            return { ...state, currentRoom:action.payload};
        case PASS_TO_SOLVE_MISSIONS:
            return { ...state, currentMission:action.payload, errorMessage: ''};
        case CURRENT_ANSWER_CHANGED:
            return { ...state, currentMission:action.payload};
        case SOLVE_MISSION:
            return { ...state, currentMission:{...currentMission, loading: true,realAnswer:''} };
        case UPDATE_ERROR_SOLVE_ROOM:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, currentMission:{...currentMission, loading: false}};
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
  };