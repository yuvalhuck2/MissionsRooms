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
  forgat_password: 'שכחת סיסמא?',
  press_here: 'לחץ כאן',
};

export const uploadStrings = {
  header: 'העלאת קבצים',
  choose_btn: 'בחר קבצים',
  approve_btn: 'אישור',
  restart_btn: 'אפס',
  return_btn: 'חזור',
  success: 'מערכת אותחלה בהצלחה',
};

export const watchOpenSolution = {
  download_btn: 'הורד קובץ',
  header: 'פתרון משימה',
  question_title: 'שאלה:',
  answer_title: 'תשובה:',
  approve_ans: 'אשר תשובה',
  reject_ans: 'דחה תשובה',
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
  wrong_ext: ' CSV כל הקבצים צריכים להיות בפורמט',
  wrong_teacher_mail: 'המייל של אחד המורים שגוי',
  wrong_student_mail: 'המייל של אחד התלמידים שגוי',
  wrong_class_mail: 'המייל של אחת הכיתות שגוי',
  wrong_class_name: 'השם של אחד הכיתות שגוי',
  class_not_found: 'חוסר התאמה בכיתות בין קובץ הקבוצות לכיתות'
};

export const openQuestionErrors = {
  mission_in_charge: 'המשתמש אינו אחראי על המשימה הנוכחית',
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

export const AddOpenAnswerMissionStrings = {
  header: 'הוספת משימה עם תשובה פתוחה',
};

export const AddDeterministicMissionErrors = {
  question_empty: 'יש להזין שאלה',
  answer_empty: 'יש להזין תשובה',
  types_empty: 'יש לבחור לפחות סוג משימה אחד',
  server_error: 'שגיאת שרת',
  points_must_be_positive: 'מספר הנקודות חייב להיות חיובי',
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
  OpenAnswerMissionButton: 'שאלה פתוחה/העלאת קובץ',
  StoryMissionButton: 'סיפור בהמשכים',
  enter_points: 'כמות נקודות עבור המשימה',
  TriviaMissionButton: 'משימת טריוויה',
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
  header: 'יש לבחור משימות',
  deterministic_name: 'סוג : שאלת פתרון יחיד',
  question: 'שאלה: ',
  search: 'חיפוש',
  deterministic_label: 'פתרון יחיד',
  story_label: 'סיפור בהמשכים',
  open_question_label: 'שאלה פתוחה/העלאת קובץ',
  trivia_label: 'טריוויה',
  choose_type: 'משימות אותן ארצה לראות',
  story_description: 'סוג: משימת סיפור בהמשכים',
  open_question_name: 'סוג: שאלה פתוחה',
  trivia_question_name: 'סוג: טריוויה',
  see_questions: 'לחצו לשאלות',
};

export const ResponseOpenAnsStrings = {
  approved: 'תשובה אושרה בהצלחה',
  rejected: 'תשובה נדחתה בהצלחה',
};

export const TeacherStrings = {
  addMission: 'הוספת משימה',
  addTemplate: 'בניית תבנית לחדר',
  createRoom: 'יצירת חדר',
  closeRoom: 'סגירת חדר',
  main_screen: 'התנתק/י',
  watch_suggestions: 'צפייה בהצעות',
  add_trivia_subject: 'נושא טריוויה',
  WatchRooms: 'צפיה בחדרים',
  trivia_manage: 'ניהול טריוויה',
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

export const CloseRoom = {
  room_closed: 'החדר נמחק בהצלחה',
  connected_students: 'המחיקה נכשלה: ישנם תלמידים מחוברים לחדר',
};

export const ChooseTempalteStrings = {
  header: 'יש לבחור תבנית לחדר',
  template_name: 'שם התבנית: ',
  minimal_missions: 'מספר משימות לקבלת בונוס: ',
  missions_presentation: 'משימות: ',
  no_tempaltes: 'אין תבניות מסוג החדר המבוקש',
  filter_by_name: 'חיפוש לפי שם התבנית',
};

export const ChooseStudentRoomStrings = {
  header: 'בחירת חדר',
  room_name: 'שם החדר: ',
  room_type: 'סוג החדר: ',
  mission_presentation: 'משימה נוכחית: ',
  solve: 'כניסה',
  no_rooms: 'אין לך חדרים פעילים',
};

export const TransferTeacherStrings={
  header:'העברת המורה',
  select_classroom:'בחר כיתה',
  select_group:'בחר קבוצה',
  ok:'אישור',
};

export const TransferStudentStrings={
    header:'העברת התלמיד',
    select_classroom:'בחר כיתה',
    select_group:'בחר קבוצה',
    ok:'אישור',
};


export const ChooseTeacherRoomStrings = {
  header: 'בחירת חדר',
  room_name: 'שם החדר: ',
  solve: 'כניסה',
  no_rooms: 'אין לך חדרים פעילים',
};

export const ChooseTeacherRoomTypeStrings = {
  classroom: 'חדרים כיתתיים',
  group: 'חדרים קבוצתיים',
  personal: 'חדרים אישיים',
  header: 'בחירת סוג חדר',
};

export const TeacherRoomMenuStrings = {
  chat: 'כניסה לצאט',
  approve_answer: 'אישור תשובות',
  close_room: 'סגירת החדר',
  mission: 'משימה',
  of: 'מתוך',
  sure: 'האם אתה בטוח שאתה רוצה למחוק את החדר?',
  yes: 'כן',
  no: 'לא',
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

export const SolveOpenQuestionMissionErrors = {
  empty_answer: 'נא למלא תשובה בטקסט או לבחור קובץ להעלאה',
};

export const StudentStrings = {
  watchMyRoom: 'החדרים שלי',
  addSuggestion: 'הוספת הצעה',
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
  room_error: 'יש תקלה עם החדר, מומלץ לנסות להתחבר אליו שנית',
  user_not_exist: 'המשתמש לא קיים',
  empty_details: 'לא מולאו פרטים',
  action_succeeded: 'הפעולה בוצעה בהצלחה!',
  classroom_not_exist: 'לפי רישומי המערכת, אין לך כיתה',
  wrong_alias: 'יש להזין שם משתמש תקין',
  mail_error: 'יש תקלה עם שליחת המייל, נא נסו שנית',
  teacher_has_classroom:'לא ניתן למחוק מורה עם כיתה',
  teacher_has_classroom_transfer:'לא ניתן להעביר מורה עם כיתה',
};

export const TransferError={
  empty_classroom_error:'לא נמצאו כיתות מתאימות',
    empty_group_error:'לא נמצאו קבוצות מתאימות',
    not_exist_classgroup_error:'הקבוצה לא קיימת',
}

export const ITStrings = {
  uploadCSV: 'העלאת קבצים',
  main_screen: 'התנתק/י',
  addNewIT: 'הוספת מנהל טכני',
  manageUsers: 'ניהול משתמשים',
  add_teacher: 'הוספת מורה',
  add_student: 'הוספת תלמיד',
  close_classroom: 'סגירת כיתה',
  delete_senior: 'מחיקת שכבת י"ב',
};

export const addMissionErrors = {
  mission_details_error: 'פרטי משימה לא תקינים',
  invalidPassRatio: 'יש להזין מספר בין 0 ל-1',
  selectTriviaQuestionsError: 'יש לבחור שאלות טריוויה',
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
  has_unapproveds_solutions: 'החדר הסתיים, אך יש פתרונות שעדיין לא אושרו',
};

export const addITStrings = {
  header: 'הוספת מנהל טכני',
  enter_alias: 'שם משתמש',
  enter_password: 'סיסמא',
  add_IT_btn: 'הוספה',
};

export const addITErrors = {
  wrong_password: 'יש להכניס סיסמא תקנית',
  wrong_alias: 'שם משתמש לא תקין',
  not_exist: 'המערכת לא זיהתה אותך, יש להתחבר מחדש ולנסות שוב',
  already_exist: 'כבר קיים משתמש עם שם משתמש זהה',
  added_successfully: ' !הוספת המנהל הטכני הצליחה',
};

export const SolveStoryMissionStrings = {
  header: 'סיפור בהמשכים',
  enter_answer: 'הכנס/י משפט',
  send_answer: 'הוספת המשפט',
  end_mission: 'סיים משימה',
};

export const StoryStrings = {
  wrong_sentence: 'משפט שגוי',
  sentence_added: 'המשפט נוסף בהצלחה',
};

export const WatchProfileStrings = {
  search_profile: 'חיפוש פרופיל',
  press_profile: 'יש ללחוץ על פרופיל משתמש על מנת לשלוח לו הודעה',
  enter_search: 'יש להזין קידומת מייל',
  name: 'שם: ',
  role: 'תפקיד: ',
  points: 'נקודות: ',
  type_message: 'יש להקליד הודעה',
  send_message: 'שלח',
  exit: 'יציאה',
};

export const ProfileErrors = {
  message_empty: 'לא ניתן לשלוח הודעה ריקה',
  message_sent: 'ההודעה נשלחה בהצלחה',
};

export const RolesStrings = {
  student: 'תלמיד',
  teacher: 'מורה',
  it: 'מנהל טכני',
  supervisor: 'מנהל מפקח',
};

export const DeleteUserString = {
  sure: 'האם אתה בטוח שאתה רוצה למחוק את המשתמש?',
  yes: 'כן',
  no: 'לא',
  deleted: 'נמחקת מהמערכת',
  sureSeniors: 'האם אתה בטוח שאתה רוצה למחוק המשתמשים?',
  deletedSuccessfully: 'תלמידים נמחקו בהצלחה',
};
export const WatchMessagesStrings = {
  sender: '',
  enter_search: 'יש להזין את קידומת מייל השולח',
  delete_message: 'מחיקה',
  date: 'תאריך: ',
  time: 'שעה: ',
  watch_messages: 'צפייה בהודעות',
  exit: 'יציאה',
};

export const MessageErrors = {
  message_not_found: 'ההודעה כבר נמחקה',
  message_deleted: 'ההודעה נמחקה בהצלחה!',
};

export const WatchSuggestionsStrings = {
  delete_suggestion: 'מחיקת הצעה',
  watch_suggestions: 'צפייה בהצעות',
  exit: 'יציאה',
  suggestion_deleted: 'ההודעה נמחקה בהצלחה',
};

export const ChangePasswordStrings = {
  header: 'שינוי סיסמא',
  enter_new_password: 'יש להכניס סיסמא חדשה',
  change_password: 'שנה סיסמא',
  password_changed: 'הסיסמא הוחלפה בהצלחה',
};

export const AllUsersStrings = {
  main_screen: 'התנתק/י',
  watchProfiles: 'חיפוש פרופיל',
  watch_messages: 'ההודעות שלי',
  changePassword: 'שינוי סיסמא',
  watchPointsTable: 'טבלת הנקודות',
};

export const PointsTableStrings = {
  header: 'טבלת השיאים',
  classroom: 'כיתתי',
  group: 'קבוצתי',
  personal: 'אישי',
  down_points: 'הורדת נקודות',
  points_label: 'נקודות',
  rank: 'מקום',
  alias: 'כינוי',
  down_points_label: 'הזנת מספר נקודות אשר ברצונך להוריד',
  minus: 'מינוס ',
};

export const PointsTableErrors = {
  negative_points: 'ניתן להוריד כמות נקודות חיובית בלבד',
  permission_problem: 'אין לך הרשאה להוריד נקודות לאישות זו',
  points_reduced: 'הנקודות הורדו בהצלחה, הטבלה לא מתעדכנת מיידית',
};

export const ManageUsersStrings = {
  edit_details: 'עריכה',
  delete_user: 'מחיקה',
  enter_last_name: 'יש להכניס שם משפחה חדש',
  enter_first_name: 'יש להכניס שם פרטי חדש',
  enable_edit_first_name: 'עריכת שם פרטי',
  enable_edit_last_name: 'עריכת שם משפחה',
  manage_users: 'ניהול משתמשים',
  press_user_for_details: 'לחצו על משתמש על מנת לערוך את נתוניו או למחוק אותו',
  transfer: 'העברת קבוצה',
};

export const Grades = {
  yud: 'י',
  yudAlef: 'יא',
  yudBeit: 'יב',
};

export const AddUserStrings = {
  student_header: 'הוספת סטודנט',
  teacher_header: 'הוספת מורה',
  enter_class_number: 'מספר כיתה',
  choose_grade: 'בחירת שכבה',
  enter_first_name: 'שם פרטי',
  enter_alias: 'קידומת מייל',
  enter_last_name: 'שם משפחה',
  choose_group: 'בחירת קבוצה',
  superviosr_label: 'מנהל מפקח',
};

export const addUserErrors = {
  already_exist: 'משתמש עם קידומת מייל זהה, קיים במערכת',
  wrong_alias: 'יש להזין קידמות מייל תקינה',
  wrong_first_name: 'יש להזין שם פרטי תקין',
  wrong_last_name: 'יש להזין שם משפחה תקין',
  wrong_class: 'הכיתה שהוזנה אינה קיימת במערכת',
  wrong_group: 'הקבוצה שהוזנה לא תקינה',
  user_added: 'המשתמש נוסף בהצלחה!',
};

export const ResetPasswordStrings = {
  header: 'איפוס סיסמא',
  reset: 'איפוס',
  enter_alias: 'יש להכניס שם משתמש',
  reset_password_succeeded: 'האיפוס הצליח! סיסמא זמנית מחכה לך במייל',
};

export const addSuggestionErrors = {
  wrong_suggestion: 'יש להזין הצעה תקינה',
  suggestion_added: 'ההצעה נוספה בהצלחה',
};

export const CloseClassroomStrings = {
  enter_class_number: 'מספר כיתה',
  choose_grade: 'בחירת שכבה',
  closeButton: 'סגירה',
  header: 'סגירת כיתה',
};

export const CloseClassroomErrors = {
  wrong_class: 'הכיתה שהוזנה אינה קיימת במערכת',
  classroom_not_empty: 'לא ניתן לסגור כיתה אשר יש בה תלמידים',
  classroom_closed: 'הכיתה נסגרה בהצלחה!',
};

export const WatchAllOpenQuestionMissions = {
  mission_title: 'משימה מספר ',
  title: 'משימות עם תשובה פתוחה בחדר: ',
};

export const TriviaManagementStrings = {
  header: 'ניהול טריוויה',
  addSubjectTrivia: 'הוסף נושא',
  addQuestionTrivia: 'הוסף שאלה',
  deleteQuestionTrivia: 'מחק שאלה',
  viewQuestions: 'צפייה בשאלות',
};

export const AddTriviaSubjectStrings = {
  header: 'הוספת נושא טריויה',
  inputPlaceholder: 'אקטואליה',
  addButtonText: 'הוסף נושא',
  labelPlaceholder: 'הקלד נושא',
  addedSubject: 'הנושא נוסף',
};

export const AddTriviaSubjectErrors = {
  alreadyExist: 'נושא טריויה כבר קיים',
  invalidSubject: 'נושא טריויה לא תקין',
};

export const AddTriviaQuestionStrings = {
  header: 'הוספת שאלת טריוויה',
  addQuestion: 'הוסף שאלה',
  addSubject: 'הכנס נושא טריוויה',
  addWrongAnswer: 'הכנס תשובה שגויה',
  addCorrectAnswer: 'הכנס תשובה נכונה',
  invalid_trivia_question_error: 'נתוני שאלה לא תקינים',
  choose_trivia_subject_error: 'אנא בחר נושא טריוויה',
  trivia_question_added: 'השאלה נוספה בהצלחה',
  selectTriviaSubjectError: 'יש לבחור נושא טריוויה',
  emptyAnswersError: 'יש למלא את כלל התשובות',
  emptyQuestionError: 'יש להזין שאלה',
  uniqueAnswersError: 'על התשובות להיות ייחודיות',
};


export const deleteTriviaQuestionStrings = {
  header: "מחק שאלת טריוויה",
  button: "מחק שאלה",
  invalid_trivia_question_error: 'נתוני שאלה לא תקינים',
  question_deleted: 'השאלה נמחקה בהצלחה!',
  question_in_mission: 'השאלה נמצאת במשימה קיימת'
}

export const SolveTriviaMissionStrings = {
  triviaPassMessage: 'המשימה הסתיימה\nציונך: עובר',
  triviaFailMessage: 'המשימה הסתיימה\nציונך: נכשל'
}
