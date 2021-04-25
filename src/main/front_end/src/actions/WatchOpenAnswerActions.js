import { baseURL } from '../api/API';
import * as FileSystem from 'expo-file-system';
import * as Sharing from "expo-sharing";
import * as Permissions from 'expo-permissions';
import * as MediaLibrary from 'expo-media-library';
import { Platform } from 'react-native';
import * as APIPaths from '../api/APIPaths';
import { GeneralErrors, ResponseOpenAnsStrings } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths';
import API from '../api/API';
import {
    RES_ANS_CLICKED,
    UPDATE_RES_ANS_ERROR,
    RES_ANS_RESET
} from '../actions/types';


import {
    Wrong_Key,
    DB_Error,
    Success,
} from './OpCodeTypes'

const {
    server_error,
    wrong_key_error,
} = GeneralErrors;

const {
    approved,
    rejected
} = ResponseOpenAnsStrings

export const downloadFile = async (apiKey, roomId, missionId, fileName) => {
    console.log(apiKey + roomId + missionId + fileName)
    uri = baseURL + "/mission/downloadFile?missionId=" + missionId + "&apiKey=" + apiKey + "&roomId=" + roomId;
    const downloadedFile = await FileSystem.downloadAsync(uri, FileSystem.documentDirectory + fileName);
    alert("s")
    const imageFileExts = ['jpg', 'png', 'gif', 'heic', 'webp', 'bmp'];

    if (Platform.OS == 'ios' && imageFileExts.every(x => !downloadedFile.uri.endsWith(x))) {
        const UTI = 'public.item';
        const shareResult = await Sharing.shareAsync(downloadedFile.uri, { UTI });
    } else {
        const perm = await Permissions.askAsync(Permissions.MEDIA_LIBRARY);
        if (perm.status != 'granted') {
            return;
        }
        try {
            const asset = await MediaLibrary.createAssetAsync(downloadedFile.uri);
            const album = await MediaLibrary.getAlbumAsync('Download');
            if (album == null) {
                await MediaLibrary.createAlbumAsync('Download', asset, false);
            } else {
                await MediaLibrary.addAssetsToAlbumAsync([asset], album, false);
            }
        } catch (e) {
            alert(e);
        }
    }
    alert("done");
}

export const responseAns = ({ apiKey, roomId, missionId, isApprove, navigation }) => {
    return async (dispatch) => {
        dispatch({ type: RES_ANS_CLICKED })
        try {
            const res = await API.post(APIPaths.responseAnswer, { apiKey, roomId, missionId, approve: isApprove });
            console.log("here")
            res
                ? checkResponseOpenAnswersResponse(res.data, dispatch, isApprove, navigation)
                : dispatch({ type: UPDATE_RES_ANS_ERROR, payload: server_error });
        } catch (err) {
            console.log(err);
            alert(err)
            dispatch({ type: UPDATE_RES_ANS_ERROR, payload: server_error });
        }
    };
}

const checkResponseOpenAnswersResponse = (data, dispatch, isApprove, navigation) => {
    const { reason, value } = data;

    switch (reason) {
        case Wrong_Key:
            return dispatch({ type: UPDATE_RES_ANS_ERROR, payload: wrong_key_error });
        case DB_Error:
            return dispatch({ type: UPDATE_RES_ANS_ERROR, payload: server_error });
        case Success:
            isApprove ? alert(approved) : alert(rejected)
            navigation.navigate(NavPaths.teacherMainScreen);
            //handleBack({navigation})
            return dispatch({ type: RES_ANS_RESET });
        default:
            return dispatch({ type: UPDATE_RES_ANS_ERROR, payload: server_error });
    }
}

export const handleBack = ({ navigation }) => {
    navigation.goBack()
}