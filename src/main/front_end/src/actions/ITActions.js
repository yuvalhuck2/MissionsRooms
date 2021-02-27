import * as DocumentPicker from 'expo-document-picker';
import { Platform } from 'react-native';
import { baseURL } from '../api/API';
import { uploadStrings, uploadStringsErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import {
  Failed_To_Read_Bytes,
  Not_Exist,
  Success,
  Wrong_File_Headers,
  Wrong_File_Name,
  Wrong_Key,
} from './OpCodeTypes';
import {
  PICKED_FILE,
  RESET_FILES,
  UPDATE_ERROR,
  UPLOAD_CSV_SUCCESS,
} from './types';

const {
  file_number_error,
  server_not_responding,
  server_error,
  wrong_key,
  wrong_headers,
  wrong_file_name,
  not_exist,
  already_exist,
} = uploadStringsErrors;

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

const parseUploadCsvResponse = (data, dispatch) => {
  const { reason, value } = data;

  switch (reason) {
    case Success:
      return dispatch({ type: UPLOAD_CSV_SUCCESS, payload: success });
    case Not_Exist:
      return dispatch({ type: UPDATE_ERROR, payload: not_exist });
    case Failed_To_Read_Bytes:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
    case Wrong_File_Name:
      return dispatch({ type: UPDATE_ERROR, payload: wrong_file_name });
    case Wrong_File_Headers:
      return dispatch({ type: UPDATE_ERROR, payload: wrong_headers });
    case Wrong_Key:
      return dispatch({ type: UPDATE_ERROR, payload: wrong_key });
    default:
      return dispatch({ type: UPDATE_ERROR, payload: server_error });
  }
};
