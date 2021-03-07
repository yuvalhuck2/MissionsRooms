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
  return_btn: 'חזור',
  success: 'מערכת אותחלה בהצלחה',
};

export const uploadStringsErrors = {
  file_number_error: 'מספר הקבצים צריך להיות 4',
  server_not_responding: 'השרת לא מגיב',
  server_error: 'שגיאת שרת',
  wrong_key: 'מפתח אימות לא תקין',
  wrong_headers: 'אחד הקבצים אינו בפורמט שהוגדר',
  wrong_file_name: 'אחד הקבצים בעל שם שגוי',
  not_exist: 'שם משתמש לא קיים',
  already_exist: 'משתמש בשם הנ"ל כבר קיים',
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

export const AddDeterministicMissionErrors = {
  question_empty: 'יש להזין שאלה',
  answer_empty: 'יש להזין תשובה',
  types_empty: 'יש לבחור לפחות סוג משימה אחד',
  server_error: 'שגיאת שרת',
};

export const AddDeterministicMissionSuccess = {
  mission_added: 'המשימה נוספה בהצלחה',
};

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
  template_added: 'התבנית נוספה בהצלחה',
};

export const AddTemplateErrors = {
  name_empty: 'יש לבחור שם תבנית לא ריק ',
  minimal_negative:
    'כמות המשימות המינימלית כדי לקבל בונוס לא יכולה להיות שלילית',
  missions_empty: '  יש לבחור לפחות משימה אחת לתבנית',
  missions_to_small:
    'יש לבחור מספר משימות הגדול מהמספר המינימלי כדי לקבל בונוס',
  type_empty: 'יש לבחור את סוג החדר',
};

export const roomTypes = [
  {
    type: 'Personal',
    translate: 'אישית',
  },
  {
    type: 'Group',
    translate: 'קבוצתית',
  },
  {
    type: 'Class',
    translate: 'כיתתי',
  },
];

export const ChooseMissionsTemplateStrings = {
  header: 'יש לבחור משימות לתבנית',
  deterministic_name: 'סוג : שאלת פתרון יחיד',
  question: 'שאלה: ',
};

export const TeacherStrings = {
  addMission: 'הוספת משימה',
  addTemplate: 'בניית תבנית לחדר',
  createRoom: 'יצירת חדר',
  closeRoom: 'סגירת חדר',
  main_screen: 'התנתק/י',
  passToRooms:'צפיה בחדרים',
};

export const AddRoomStrings = {
  header: 'הוספת חדר',
  enter_name: 'שם החדר',
  enter_bonus: 'בונוס על סיום החדר',
  enter_student: 'בחר/י תלמיד',
  enter_group: 'בחר/י קבוצה',
  classroom_add: 'הוספת חדר כיתתי',
  group_add: 'הוספת חדר קבוצתי',
  student_add: 'הוספת חדר אישי',
};

export const AddRoomErrors = {
  name_empty: 'יש לבחור שם חדר לא ריק ',
  bonus_empty_negative: 'יש לבחור בונוס לא שלילי',
  classroom_empty: 'הכיתה שלך לא מעודכנת במערכת אנא פנה/י למחלקה הטכנית',
  group_empty: 'יש לזין קבוצה על מנת להוסיף חדר קבוצתי',
  student_empty: 'יש להזין תלמיד על מנת להוסיף חדר אישי',
  empty_template: 'יש לבחור תבנית לחדר',
  room_added: 'החדר נוסף בהצלחה',
};

export const ChooseTempalteStrings = {
  header: 'יש לבחור תבנית לחדר',
  template_name: 'שם התבנית: ',
  minimal_missions: 'מספר משימות לקבלת בונוס: ',
  missions_presentation: 'משימות: ',
  no_tempaltes: 'אין תבניות מסוג החדר המבוקש',
};

export const ChooseStudentRoomStrings = {
  header: 'בחירת חדר',
  room_name: 'שם החדר: ',
  room_type: 'סוג החדר: ',
  mission_presentation: 'משימה נוכחית: ',
  solve: 'כניסה',
  no_rooms: 'אין לך חדרים פעילים',
};

export const ChooseRoomStudentErrors = {
  room_empty: 'יש לבחור חדר',
  wrong_answer: 'תשובה לא נכונה, מספר נסיונות מותרים: ',
  fail: 'נכשלת במשימה,לא נורא בפעם הבאה יהיה יותר טוב:(',
  pass: 'תשובה נכונה!',
};

export const SolveDeterministicMissionStrings = {
  enter_answer: 'הכנס/י תשובה',
  send_answer: 'שלח/י תשובה',
};

export const StudentStrings = {
  watchMyRoom: 'החדרים שלי',
  addSuggestion:'הוספת הצעה',
  main_screen: 'התנתק/י',
};

export const AddSuggestionStrings = {
    header: 'הוספת הצעה',
    addSuggestionText: 'הכנס/י הצעה',
    send_suggestion: 'שלח/י',
};

export const registerCodeErrors = {
  wrong_alias_register_code: 'שם משתמש לא תקין',
  wrong_register_code: 'קוד לא תקין',
  not_registered_register_code: 'משתמש לא רשום',
  code_not_match_register_code: 'קוד לא תואם',
  wrong_type_register_code: 'סוג קבוצה לא תקינה',
  already_exist_register_code: 'משתמש כבר רשום',
  not_exist_group_register_code: 'קבוצה לא קיימת',
  already_exist_student_register_code: 'תלמיד כבר רשום',
};

export const GeneralErrors = {
  server_error: 'שגיאת שרת',
  wrong_key_error: 'שגיאת מפתח הזדהות, יש לשלוח בקשה מחדש',
  teacher_not_exists_error: 'מורה לא קיים',
  student_not_exist: 'סטודנט לא קיים',
  room_error:'יש תקלה עם החדר, מומלץ לנסות להתחבר אליו שנית',
};

export const ITStrings = {
  uploadCSV: 'העלאת קבצים',
  main_screen: 'התנתק/י',
  addNewIT: 'הוספת מנהל טכני',
};

export const addMissionErrors = {
  mission_details_error: 'פרטי משימה לא תקינים',
};

export const addRoomTemplateErrors = {
  wrong_name_room_template_error: 'שם תבנית לא תקין',
  wrong_amount_room_template_error: 'הוזן מספר משימות שלילי',
  big_amount_room_template_error: 'הוזן מספר משימות מעבר למירבי',
  wrong_list_room_template_error: 'משימות בחדר אינן תקינות',
  not_mission_room_template_error: 'משימה לא קיימת',
  type_not_match_room_template_error: 'משימה לא מתאימה לסוג החדר',
};

export const AddRoomResponseErrors = {
  null_room_add_room_error: 'חדר לא קיים',
  wrong_name_add_room_error: 'שם חדר ריק',
  wrong_bonus_add_room_error: 'יש להזין בונוס אי-שלילי',
  not_exist_template_add_room_error: 'תבנית של החדר שהוזן לא קיימת',
  type_not_match_add_room_error: 'סוג החדר שהוזן לא תואם לסוג התבנית',
  wrong_type_add_room_error: 'סוג החדר לא תקין',
  not_exist_student_add_room_error: 'התלמיד המשויך לחדר אינו קיים',
  already_exist_student_add_room_error: 'התלמיד שהוזן כבר משויך לחדר מסוג אישי',
  not_exist_group_add_room_error: 'הקבוצה המשויכת לחדר אינה קיימת',
  already_exist_group_add_room_error:
    'הקבוצה שהוזנה כבר משויכת לחדר מסוג קבוצתי',
  not_exist_classroom_add_room_error: 'הכיתה המשויכת לחדר אינה קיימת',
  already_exist_class_add_room_error: 'הכיתה שהוזנה כבר משויכת לחדר מסוג כיתתי',
};

export const passToMyRoomsErrors = {
  student_not_exist_in_class_error: 'תלמיד לא קיים בכיתה',
  student_not_exist_in_group_error: 'תלמיד לא קיים בקבוצה',
  wrong_mission_error: 'משימה לא תקינה',
};

export const webSocketMessages = {
  final: '\nהחדר הסתיים',
}

export const addITStrings = {
  header: 'הוספת מנהל טכני',
  enter_alias: 'שם משתמש',
  enter_password: 'סיסמא',
  add_IT_btn: 'הוספה',
}

export const addITErrors = {
  wrong_password: 'יש להכניס סיסמא תקנית',
  wrong_alias: 'שם משתמש לא תקין',
  not_exist:'המערכת לא זיהתה אותך, יש להתחבר מחדש ולנסות שוב',
  already_exist:'כבר קיים משתמש עם שם משתמש זהה',
  added_successfully:' !הוספת המנהל הטכני הצליחה',
}