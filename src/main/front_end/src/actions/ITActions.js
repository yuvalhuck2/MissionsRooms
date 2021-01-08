import * as DocumentPicker from 'expo-document-picker';
import { Platform } from 'react-native';
import { uploadStringsErrors } from '../locale/locale_heb';
import {PICKED_FILE, RESET_FILES} from './types'
import * as NavPaths from '../navigation/NavPaths';
import API from '../api/API';

const {
    file_number_error
} = uploadStringsErrors

export const onPickFile = () => {
    return async (dispatch) => {
        DocumentPicker.getDocumentAsync({
            type: 'text/comma-separated-values',
            multiple: true,
            copyToCacheDirectory: true
        }).then(res => {
            if (res != undefined && res != null && res.type != 'cancel') {
                dispatch({type: PICKED_FILE, payload: res});
            }
        }).catch(err => alert("Document Picker Error"));
    }
}

export const onRestart = () => {
    return {type: RESET_FILES}
}

export const onSendFiles = ({files, apiKey}) => {
    return async (dispatch) => {
        const formData = new FormData();
        if (files.length != 4){
            alert(file_number_error)
            return
        }
        files.forEach(file => {
            formData.append("files", {
                uri: Platform.OS === 'android' ? file.uri : file.uri.replace('file://', ''),
                type: "*/*",
                name: file.name
            });
        });  
        const xhr = new XMLHttpRequest();
        // 2. open request
        let baseUri = API.getUri();
        let uri = baseUri+"/uploadCsv?token="+apiKey
        xhr.open('POST', uri);
        //Send the proper header information along with the request
        xhr.setRequestHeader('Content-type', 'multipart/form-data');
        // 3. set up callback for request
        xhr.onload = () => {
            const response = JSON.parse(xhr.response);
            //TODO: parse by code
            alert(response);
            // ... do something with the successful response
        };
        // 4. catch for request error
        xhr.onerror = e => {
            alert('upload failed');
        };
        // 4. catch for request timeout
        xhr.ontimeout = e => {
            alert('upload timeout');
        };


        xhr.onreadystatechange = function () {//Call a function when the state changes.
            alert(xhr.responseText);
        }

        xhr.send(formData);
        dispatch({type: RESET_FILES});
    }
}


export const navigateToUploadCSV = ({ navigation }) => {
    navigation.navigate(NavPaths.uploadCsv);
    return { type: CLEAR_STATE };
  };