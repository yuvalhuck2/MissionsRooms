import axios from 'axios'

export const baseURL = "http://132.73.220.233:8080"
  // export const baseURL = "http://192.168.1.12:8080"
//export const baseURL = "http://132.73.209.45:8080"
//export const baseURL = "http://192.168.1.12:8080"
export default axios.create({
    baseURL: baseURL,
  });

  // THIS IS FOR CHECKING DNS ADDRESS WHEN RUNNING EXPO ON PHYSICAL DEVICE
//    import Constants from 'expo-constants'
// const { manifest } = Constants;

// const uri = `http://${manifest.debuggerHost.split(':').shift()}:8080`;

// alert(uri);