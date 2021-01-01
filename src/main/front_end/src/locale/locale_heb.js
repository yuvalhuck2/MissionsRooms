export const registerStrings = {
  header: 'ברוך הבא לכיתה וירטואלית',
  enter_email: 'הכנס מייל',
  enter_password: 'הכנס סיסמא',
  register_btn: 'לחץ להרשמה',
  already_user: 'כבר יש ברשותך משתמש? ',
  already_user_sign_in: 'התחבר כאן',
};

export const registerErrors = {
  wrong_password: 'אנא הכנס סיסמא תקנית',
  wrong_alias: 'אנא הכנס שם משתמש תקין',
  server_error: 'שגיאת שרת',
  not_exist: 'שם משתמש לא קיים',
  already_exist: 'משתמש בשם הנ"ל כבר קיים',
}

export const loginStrings = {
    header: 'התחבר כאן',
    enter_email: 'הכנס מייל',
    enter_password: 'הכנס סיסמא',
    login_btn: 'לחץ להתחברות',
    no_user: 'אין ברשותך משתמש? ',
    no_user_sign_up: 'הירשם כאן',
  };

export const AddDeterministicMissionStrings = {
    header: ' הוספת משימת פתרון יחיד',
    enter_question: 'הכנס/י שאלה',
    enter_answer: 'הכנס/י תשובה',
    personal: 'אישית',
    group:'קבוצתית',
    classroom:'כיתתית',
  };

  export const AddDeterministicMissionErrors={
    question_empty:'יש להזין שאלה',
    answer_empty:'יש להזין תשובה',
    types_empty:'יש לבחור לפחות סוג משימה אחד',
  }

  export const AddStrings = {
    addButton: 'הוספה',
  };

  export const ChooseMissionToAddStrings = {
    header: ' יש לבחור סוג משימה להוספה',
    deterministicButton: 'שאלה עם פתרון יחיד',
  };

  export const AddRoomTempalteStrings={
    header: ' הוספת תבנית של חדר ',
    enter_name:'שם התבנית',
    enter_type:'סוג התבנית',
    enter_minimal_amount:'כמות משימות מינימלית לקבלת בונוס',
    move_to_missions:'מעבר לבחירת המשימות',
  }

  export const AddTemplateErrors={
    name_empty:'יש לבחור שם תבנית לא ריק ',
    minimal_negative:'כמות המשימות המינימלית כדי לקבל בונוס לא יכולה להיות שלילית',
    missions_empty:'יש לבחור לפחות משימה אחת לתבנית',
    type_empty:'יש לבחור את סוג החדר',
  }

  export const roomTypes=[
    {
      type:'Personal',
      translate:'אישית'},
    {
      type:'Group',
      translate:'קבוצתית'
    },
    {
      type:'Class',
      translate:'כיתתי'
    },
  ]