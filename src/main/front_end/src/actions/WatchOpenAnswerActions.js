import { baseURL } from '../api/API';
import * as FileSystem from 'expo-file-system';
import * as Sharing from "expo-sharing";
import * as Permissions from 'expo-permissions';
import * as MediaLibrary from 'expo-media-library';
import { Platform } from 'react-native';


export const downloadFile = async (apiKey, roomId, missionId, fileName) => {
    console.log("download");
    console.log( apiKey + roomId + missionId + fileName )
    alert(FileSystem.documentDirectory);
    uri = "http://" + baseURL +  "/mission/downloadFile?missionId="+missionId+"&apiKey="+apiKey+"&roomId="+roomId;
    const downloadedFile = await FileSystem.downloadAsync(uri, FileSystem.documentDirectory + fileName);
    alert(downloadedFile.headers);
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