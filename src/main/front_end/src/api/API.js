import axios from 'axios'

export const baseURL = "http://10.100.102.5:8080"
//export const baseURL = "http://192.168.0.10:8080"
export default axios.create({
    baseURL: baseURL,
  });