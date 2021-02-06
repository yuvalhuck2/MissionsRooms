import axios from 'axios'

export const baseURL = "http://10.0.0.14:8080"

export default axios.create({
    baseURL: baseURL,
  });