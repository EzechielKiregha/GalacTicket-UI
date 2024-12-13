import axios from 'axios'

const baseurl = 'https://localhost:8080/';
const AxiosInstance = axios.create({
    baseURL : baseurl,
    timeout : 5000,
    headers : {
        "Content-Type" : 'application/json',
        Accept : 'application/json'
    }
})

export default AxiosInstance