import { baseURL } from '../api/API';
import * as FileSystem from 'expo-file-system';
import * as Sharing from "expo-sharing";
import * as Permissions from 'expo-permissions';
import * as MediaLibrary from 'expo-media-library';
import { Platform } from 'react-native';
import * as APIPaths from '../api/APIPaths';
import { GeneralErrors } from '../locale/locale_heb';
import API from '../api/API';
import {
    RES_ANS_CLICKED,
    UPDATE_RES_ANS_ERROR,
    RES_ANS_RESET
} from '../actions/types';

const {
    server_error,
    wrong_key_error,
} = GeneralErrors;

export const downloadFile = async (apiKey, roomId, missionId, fileName) => {
    console.log( apiKey + roomId + missionId + fileName )
    uri =  baseURL + "/mission/downloadFile?missionId=" + missionId + "&apiKey=" + apiKey + "&roomId=" + roomId;
    const downloadedFile = await FileSystem.downloadAsync(uri, FileSystem.documentDirectory + fileName);
    const imageFileExts = ['jpg', 'png', 'gif', 'heic', 'webp', 'bmp'];

    if (Platform.OS == 'ios' && imageFileExts.every(x => !downloadedFile.uri.endsWith(x))) {
        const UTI = 'public.item';
        const shareResult = await Sharing.shareAsync(downloadedFile.uri, { UTI });
    } else {
        const perm = await Permissions.askAsync(Permissions.MEDIA_LIBRARY);
        if (perm.status != 'granted') {
            alert("Status not granted")
            return;
        }
        try {
            const asset = await MediaLibrary.createAssetAsync(downloadedFile.uri);
            const album = await MediaLibrary.getAlbumAsync('Downloadd');
            if (album == null) {
                await MediaLibrary.createAlbumAsync('Downloadd', asset, false);
            } else {
                await MediaLibrary.addAssetsToAlbumAsync([asset], album, false);
            }
            alert("done");
        } catch (e) {
            alert(e);
        }
    }
}

export const responseAns = ({ apiKey, roomId, missionId, isApprove }) => {
    return async (dispatch) => {
        dispatch({type: RES_ANS_CLICKED})
        try {
            const res = await API.post(APIPaths.responseAnswer, { apiKey, roomId, missionId, isApprove });
            console.log(res)
            res
                ? checkResponseOpenAnswersResponse(res.data, dispatch)
                : dispatch({ type: UPDATE_RES_ANS_ERROR, payload: server_error+1 });
        } catch (err) {
            console.log(err);
            alert(err)
            dispatch({ type: UPDATE_RES_ANS_ERROR, payload: server_error+2 });
        }
    };
}

const checkResponseOpenAnswersResponse = (data, dispatch) => {
    const { reason, value } = data;

    switch (reason) {
        case Wrong_Key:
            return dispatch({ type: UPDATE_RES_ANS_ERROR, payload: wrong_key_error });
        case DB_Error:
            return dispatch({ type: UPDATE_RES_ANS_ERROR, payload: server_error+3});
        case Success:
            alert(added_successfully)
            return dispatch({ type: RES_ANS_RESET });
        default:
            return dispatch({ type: UPDATE_RES_ANS_ERROR, payload: server_error+4 });
    }
}