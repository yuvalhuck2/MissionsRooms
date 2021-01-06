const initialState = {
    roomName: '',
    student:'',
    group:'',
    classroom:{name:'class',
        groups:[
            {name:'groupA',
            groupType:'A',
            students:[
                {alias:'shlomo',
                firstName:'יובל',
                lastName:'סבג',},
                {alias:'shula',
                firstName:'שולה',
                lastName:'שלושלה',}
            ]
        }
        ]
    },
    participantKey:'',
    bonus: '0',
    roomTemplate:'',
    loading: false,
    type: '',
    presentedTemplates:[],
    allTemplates:[{
        id:"123",
        name:" תבנית ליחיד",
        type:"Personal",
        minimalMissionsToPass:"3",
        missions:[{missionId:"3",name:"Known answer mission",question:['שאלה']
        ,missionTypes:['Personal']},{missionId:"6",name:"Known answer mission",question:['שאלה אחרת']
        ,missionTypes:['Personal']}],
        
    },
    {
        id:"123",
        name:"תבנית לקבוצה",
        type:"Group",
        minimalMissionsToPass:"3",
        missions:[{missionId:"3",name:"Known answer mission",question:['שאלה']
        ,missionTypes:['Personal']},{missionId:"6",name:"Known answer mission",question:['שאלה אחרת']
        ,missionTypes:['Personal']}],
        
    },
    {
        id:"123",
        name:"תבנית לכיתה",
        type:"Class",
        minimalMissionsToPass:"3",
        missions:[{missionId:"3",name:"Known answer mission",question:['שאלה']
        ,missionTypes:['Personal']},{missionId:"6",name:"Known answer mission",question:['שאלה אחרת']
        ,missionTypes:['Personal']}],
        
    }],
    errorMessage: '',
  };

  import {
    ROOM_NAME_CHANGED,
    BONUS_CHANGED,
    ADD_ROOM,
    TEMPLATE_CHANGED,
    GROUP_CHANGED,
    STUDENT_CHANGED,
    UPDATE_ERROR_ROOM,
    CLEAR_STATE,
    PASS,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case ROOM_NAME_CHANGED:
            return { ...state, roomName: action.payload };
        case BONUS_CHANGED:
            return { ...state, bonus: action.payload };
        case TEMPLATE_CHANGED:
            return { ...state, roomTemplate: action.payload };
        case GROUP_CHANGED:
            return { ...state, group: action.payload };
        case STUDENT_CHANGED:
            return { ...state, student: action.payload };
        case PASS:
            return { ...state, participantKey: action.payload.participant,
                type: action.payload.roomType, errorMessage: '', presentedTemplates:action.payload.templates };
        case ADD_ROOM:
            return { ...state, loading: true };
        case UPDATE_ERROR_ROOM:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading: false };
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
  };