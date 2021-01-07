export const registerStrings = {
  header: 'ברוך הבא לכיתה וירטואלית',
  enter_email: 'הכנס מייל',
  enter_password: 'הכנס סיסמא',
  register_btn: 'לחץ להרשמה',
  already_user: 'כבר יש ברשותך משתמש? ',
  already_user_sign_in: 'התחבר כאן',
};

export const authErrors = {
  wrong_password_login: 'סיסמא לא תקנית או לא תואמת',
  wrong_password: 'אנא הכנס סיסמא תקנית',
  wrong_alias: 'אנא הכנס שם משתמש תקין',
  server_error: 'שגיאת שרת',
  not_exist: 'שם משתמש לא קיים',
  already_exist: 'משתמש בשם הנ"ל כבר קיים',
};

export const loginStrings = {
    header: 'התחבר כאן',
    enter_email: 'הכנס מייל',
    enter_password: 'הכנס סיסמא',
    login_btn: 'לחץ להתחברות',
    no_user: 'אין ברשותך משתמש? ',
    no_user_sign_up: 'הירשם כאן',
  };

  export const uploadStrings = {
    header: 'העלאת קבצים',
    choose_btn: 'בחר קבצים',
    approve_btn: 'אישור',
    restart_btn: 'אפס',
  };

  export const uploadStringsErrors = {
    file_number_error: "מספר הקבצים צריך להיות 4"
};

export const authStrings = {
  header: 'הכנס קוד אימות',
  enter_code: 'הכנס קוד',
  choose_group: 'בחר קבוצה',
  send_btn: 'שלח',
  no_code: 'לא קיבלת קוד? ',
};
export const AddDeterministicMissionStrings = {
  header: ' הוספת משימת פתרון יחיד',
  enter_question: 'הכנס/י שאלה',
  enter_answer: 'הכנס/י תשובה',
  personal: 'אישית',
  group: 'קבוצתית',
  classroom: 'כיתתית',
};

  export const AddDeterministicMissionErrors={
    question_empty:'יש להזין שאלה',
    answer_empty:'יש להזין תשובה',
    types_empty:'יש לבחור לפחות סוג משימה אחד',
    server_error: 'שגיאת שרת',
  }

  export const AddDeterministicMissionSuccess={
    mission_added:'המשימה נוספה בהצלחה'
  }

export const AddStrings = {
  addButton: 'הוספה',
};

export const ChooseMissionToAddStrings = {
  header: ' יש לבחור סוג משימה להוספה',
  deterministicButton: 'שאלה עם פתרון יחיד',
};

export const AddRoomTempalteStrings = {
  header: ' הוספת תבנית של חדר ',
  enter_name: 'שם התבנית',
  enter_type: 'סוג התבנית',
  enter_minimal_amount: 'כמות משימות מינימלית לקבלת בונוס',
  move_to_missions: 'מעבר לבחירת המשימות',
  template_added:'התבנית נוספה בהצלחה',
}

  export const AddTemplateErrors={
    name_empty:'יש לבחור שם תבנית לא ריק ',
    minimal_negative:'כמות המשימות המינימלית כדי לקבל בונוס לא יכולה להיות שלילית',
    missions_empty:'  יש לבחור לפחות משימה אחת לתבנית',
    missions_to_small:'יש לבחור מספר משימות הגדול מהמספר המינימלי כדי לקבל בונוס',
    type_empty:'יש לבחור את סוג החדר',
  }

export const roomTypes = [
  {
    type: 'Personal',
    translate: 'אישית'
  },
  {
    type: 'Group',
    translate: 'קבוצתית'
  },
  {
    type: 'Class',
    translate: 'כיתתי'
  },
]

  export const ChooseMissionsTemplateStrings={
    header: 'יש לבחור משימות לתבנית',
    deterministic_name:'סוג : שאלת פתרון יחיד',
    question:'שאלה: ',
  }

  export const TeacherStrings={
    addMission:'הוספת משימה',
    addTemplate:'בניית תבנית לחדר',
    createRoom:'יצירת חדר',
    closeRoom:'סגירת חדר',
  }

  export const AddRoomStrings={
    header:'הוספת חדר',
    enter_name:'שם החדר',
    enter_bonus:'בונוס על סיום החדר',
    enter_student:'בחר/י תלמיד',
    enter_group:'בחר/י קבוצה',
    classroom_add:'הוספת חדר כיתתי',
    group_add:'הוספת חדר קבוצתי',
    student_add:'הוספת חדר אישי',
    
  }

  export const AddRoomErrors={
    name_empty:'יש לבחור שם חדר לא ריק ',
    bonus_empty_negative:'יש לבחור בונוס לא שלילי',
    classroom_empty:'הכיתה שלך לא מעודכנת במערכת אנא פנה\י למחלקה הטכנית',
    group_empty:'יש לזין קבוצה על מנת להוסיף חדר קבוצתי',
    student_empty:'יש להזין תלמיד על מנת להוסיף חדר אישי',
    empty_template:'יש לבחור תבנית לחדר',
  }

  export const ChooseTempalteStrings={
    header:'יש לבחור תבנית לחדר',
    template_name:'שם התבנית: ',
    minimal_missions:'מספר משימות לקבלת בונוס: ',
    missions_presentation:'משימות: ',
  }

  export const ChooseStudentRoomStrings={
    header:'בחירת חדר',
    room_name:'שם החדר: ',
    room_type:'סוג החדר: ',
    mission_presentation:'משימה נוכחית: ',
    solve:'כניסה',
  }

  export const ChooseRoomStudentErrors={
    room_empty:'יש לבחור חדר',
    wrong_answer:'תשובה לא נכונה, מספר נסיונות מותרים: ',
  };

  export const SolveDeterministicMissionStrings={
    enter_answer:'הכנס\י תשובה',
    send_answer:'שלח\י תשובה',
  }

  export const StudentStrings={
    watchMyRoom:'החדרים שלי',
  }

  export const registerCodeErrors = {
    wrong_alias_register_code: "שם משתמש לא תקין",
    wrong_register_code: "קוד לא תקין",
    not_registered_register_code: "משתמש לא רשום",
    code_not_match_register_code: "קוד לא תואם",
    wrong_type_register_code: "סוג קבוצה לא תקינה",
    already_exist_register_code: "משתמש כבר רשום",
    not_exist_group_register_code: "קבוצה לא קיימת",
    already_exist_student_register_code: "תלמיד כבר רשום"
  }

  export const GeneralErrors={
    server_error: 'שגיאת שרת',
  }