import axios from 'axios'
export const baseURL = "http://132.72.232.60:8080"
//export const baseURL = "http:///192.168.14.55:8080"
export default axios.create({
    baseURL: baseURL,
  });

  // THIS IS FOR CHECKING DNS ADDRESS WHEN RUNNING EXPO ON PHYSICAL DEVICE
//    import Constants from 'expo-constants'
// const { manifest } = Constants;

// const uri = `http://${manifest.debuggerHost.split(':').shift()}:8080`;

// alert(uri);