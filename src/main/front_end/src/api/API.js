import axios from 'axios'

//export const baseURL = "http://132.72.47.211:8080"
 // export const baseURL = "http://192.168.1.12:8080"
//export const baseURL = "http://10.0.0.8:8080"
export const baseURL = "132.72.234.180:8080"
export default axios.create({
    baseURL: baseURL,
  });

  // THIS IS FOR CHECKING DNS ADDRESS WHEN RUNNING EXPO ON PHYSICAL DEVICE
//    import Constants from 'expo-constants'
// const { manifest } = Constants;

// const uri = `http://${manifest.debuggerHost.split(':').shift()}:8080`;

// alert(uri);