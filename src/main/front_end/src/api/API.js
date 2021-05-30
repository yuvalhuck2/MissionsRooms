import axios from 'axios'
import https from 'https';
//export const baseURL = "https://192.168.1.39:8080"
export const baseURL = "https://192.168.1.18:8443"
const agent = new https.Agent({rejectUnauthorized: false})
export default axios.create({
    baseURL: baseURL,
    httpsAgent: agent
  });

  // THIS IS FOR CHECKING DNS ADDRESS WHEN RUNNING EXPO ON PHYSICAL DEVICE
//    import Constants from 'expo-constants'
// const { manifest } = Constants;

// const uri = `http://${manifest.debuggerHost.split(':').shift()}:8080`;

// alert(uri);