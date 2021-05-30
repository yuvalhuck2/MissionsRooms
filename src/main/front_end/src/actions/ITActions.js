import * as DocumentPicker from 'expo-document-picker';
import { Platform } from 'react-native';
import { baseURL } from '../api/API';
import { GeneralErrors, uploadStrings, uploadStringsErrors, DeleteUserString } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import * as APIPaths from '../api/APIPaths';
import API from '../api/API';
import {
  Failed_To_Read_Bytes,
  Not_Exist,
  Success,
  Wrong_Classroom,
  Wrong_File_Headers,
  Wrong_File_Name,
  Wrong_Key,
  WrongFileExt,
  InvalidTeacherMail,
  InvalidStudentMail,
  InvalidClassMail,
  InvalidClassName,
  ClassNotFound
} from './OpCodeTypes';
import {
  PICKED_FILE,
  RESET_FILES,
  RESET_MANAGE_USERS,
  UPDATE_ERROR,
  UPDATE_ERROR_MANAGE_USERS,
  UPLOAD_CSV_SUCCESS,
  UPDATE_ALL_USER_PROFILES_MANAGE_USERS,
  IS_STUDENT_USER_CHANGED,
  UPDATE_ERROR_DELETE_SENIOR,
  DELETE_ALL_SENIOR_STUDENTS,
  RESET_IT
} from './types';

const {
  wrong_key_error,
  action_succeeded,
} = GeneralErrors;

const {
  file_number_error,
  server_not_responding,
  server_error,
  wrong_key,
  wrong_headers,
  wrong_file_name,
  not_exist,
  already_exist,
  wrong_ext,
  wrong_teacher_mail,
  wrong_student_mail,
  wrong_class_mail,
  wrong_class_name,
  class_not_found
} = uploadStringsErrors;

const {
  deletedSuccessfully,
} = DeleteUserString

const { success } = uploadStrings;

export const onPickFile = () => {
  return async (dispatch) => {
    DocumentPicker.getDocumentAsync({
      type: 'text/comma-separated-values',
      multiple: true,
      copyToCacheDirectory: true,
    })
      .then((res) => {
        if (res != undefined && res != null && res.type != 'cancel') {
          dispatch({ type: PICKED_FILE, payload: res });
        }
      })
      .catch((err) => alert('Document Picker Error'));
  };
};

export const onRestart = () => {
  return { type: RESET_FILES };
};

export const onSendFiles = ({ files, apiKey }) => {
  return async (dispatch) => {
    const formData = new FormData();
    if (files.length != 4) {
      dispatch({ type: UPDATE_ERROR, payload: file_number_error });
      return;
    }
    files.forEach((file) => {
      formData.append('files', {
        uri:
          Platform.OS === 'android'
            ? file.uri
            : file.uri.replace('file://', ''),
        type: '*/*',
        name: file.name,
      });
    });
    const xhr = new XMLHttpRequest();
    // 2. open request
    let uri = baseURL + '/uploadCsv?token=' + apiKey;
    xhr.open('POST', uri);
    //Send the proper header information along with the request
    xhr.setRequestHeader('Content-type', 'multipart/form-data');
    // 3. set up callback for request
    xhr.onload = () => {
      const response = JSON.parse(xhr.responseText);
      parseUploadCsvResponse(response, dispatch);
    };
    // 4. catch for request error
    xhr.onerror = (e) => {
      dispatch({ type: UPDATE_ERROR, payload: server_error });
    };
    // 4. catch for request timeout
    xhr.ontimeout = (e) => {
      dispatch({ type: UPDATE_ERROR, payload: server_not_responding });
    };

    // xhr.onreadystatechange = function () {//Call a function when the state changes.
    //     alert(xhr.responseText);
    // }

    xhr.send(formData);
    dispatch({ type: RESET_FILES });
  };
};

export const navigateToUploadCSV = ({ navigation }) => {
  navigation.navigate(NavPaths.uploadCsv);
  return { type: '' };
};

export const navigateToAddNewIT = ({ navigation }) => {
  navigation.navigate(NavPaths.addIT);
  return { type: '' };
};

export const parseUploadCsvResponse = (data, dispatch) => {
  const { reason, value } = data;

  switch (reason) {
    case Success:
      return dispatch({ type: UPLOAD_CSV_SUCCESS, payload: success });
    case Not_Exist:
      return dispatch({ type: UPDATE_ERROR_IT, payload: not_exist });
    case Failed_To_Read_Bytes:
      return dispatch({ type: UPDATE_ERROR_IT, payload: server_error });
    case Wrong_File_Name:
      return dispatch({ type: UPDATE_ERROR_IT, payload: wrong_file_name });
    case Wrong_File_Headers:
      return dispatch({ type: UPDATE_ERROR_IT, payload: wrong_headers });
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR_IT, payload: wrong_key });
    case WrongFileExt:
      return dispatch({ type: UPDATE_ERROR_IT, payload: wrong_ext });
    case InvalidTeacherMail:
      return dispatch({ type: UPDATE_ERROR_IT, payload: wrong_teacher_mail });
    case InvalidStudentMail:
      return dispatch({ type: UPDATE_ERROR_IT, payload: wrong_student_mail });
    case InvalidClassMail:
      return dispatch({ type: UPDATE_ERROR_IT, payload: wrong_class_mail });
    case InvalidClassName:
      return dispatch({ type: UPDATE_ERROR_IT, payload: wrong_class_name });
    case ClassNotFound:
      return dispatch({ type: UPDATE_ERROR_IT, payload: Wrong_Classroom });
    default:
      return dispatch({ type: UPDATE_ERROR_IT, payload: server_error });
  }
};

export const passToManageUsers = ({ navigation, apiKey }) => {
  return async (dispatch) => {
    dispatch({ type: RESET_MANAGE_USERS, payload: apiKey });
    try {
      const res = await API.post(APIPaths.getAllSchoolUsers, { apiKey });
      if (res) {
        checkManageUsersResponse({
          data: res.data,
          dispatch,
          navigation,
        });
      } else {
        dispatch({ type: UPDATE_ERROR_MANAGE_USERS, payload: server_error });
      }
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_MANAGE_USERS, payload: server_error });
    }
  };
}

const checkManageUsersResponse = ({ data, dispatch, navigation }) => {
  const { reason, value } = data;
  switch (reason) {
    case Not_Exist:
      return dispatch({
        type: UPDATE_ERROR_MANAGE_USERS,
        payload: wrong_key_error,
      });
    case Success:
      dispatch({ type: UPDATE_ALL_USER_PROFILES_MANAGE_USERS, payload: value });
      return navigation.navigate(NavPaths.manageUsers);
    default:
      return dispatch({ type: UPDATE_ERROR_MANAGE_USERS, payload: server_error });
  }
}

export const passToAddStudent = ({ navigation }) => {
  navigation.navigate(NavPaths.addUser);
  return { type: IS_STUDENT_USER_CHANGED, payload: true };

}

export const passToAddTeacher = ({ navigation }) => {
  navigation.navigate(NavPaths.addUser);
  return { type: IS_STUDENT_USER_CHANGED, payload: false };
}

export const deleteSeniorStudents = ({ apiKey, navigation }) => {
  return async (dispatch) => {

    try {
      const res = await API.post(APIPaths.deleteSeniors, { apiKey });
      if (res) {
        checkSeniorStudentsResponse({
          data: res.data,
          dispatch,
          navigation,
        });
      } else {
        dispatch({ type: UPDATE_ERROR_DELETE_SENIOR, payload: server_error });
      }
    } catch (err) {
      console.log(err);
      return dispatch({ type: UPDATE_ERROR_DELETE_SENIOR, payload: server_error });
    }
  };
}

const checkSeniorStudentsResponse = ({ data, dispatch, navigation }) => {
  const { reason, value } = data;
  switch (reason) {
    case Wrong_Key:
      return dispatch({
        type: UPDATE_ERROR_DELETE_SENIOR,
        payload: wrong_key_error,
      });
    case Success:

      alert(value + " " + deletedSuccessfully);
      return dispatch({ type: DELETE_ALL_SENIOR_STUDENTS, payload: value });

    default:
      return dispatch({ type: UPDATE_ERROR_DELETE_SENIOR, payload: server_error });
  }
}

export const handleBack = ({navigation,apiKey}) =>{
  navigation.goBack()
  return {
      type: RESET_IT,
      payload: apiKey
  }
}
