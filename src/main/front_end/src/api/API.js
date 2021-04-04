import axios from 'axios'
export const baseURL = "http://192.168.1.34:8080"
//export const baseURL = "http:///192.168.14.55:8080"
export default axios.create({
    baseURL: baseURL,
  });