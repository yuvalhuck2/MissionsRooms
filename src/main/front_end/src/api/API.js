import axios from 'axios'

export const baseURL = "http://10.100.102.5:8080"
//export const baseURL = "http://132.72.47.211:8080"
export default axios.create({
    baseURL: baseURL,
  });