import router from '../router'
import axios from "axios";
import {ElMessage} from "element-plus";

const request = axios.create({
    baseURL: import.meta.env.VITE_BASE_URL,
    timeout: 30000
})

// Request interceptor
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    const user = JSON.parse(localStorage.getItem('xm-user') || '{}')
    config.headers['token'] = user.token || ''
    return config
}, error => {
    return Promise.reject(error)
});

// Response interceptor
request.interceptors.response.use(
    response => {
        let res = response.data;
        // If type is file
        if (response.config.responseType === 'blob') {
            return res
        }
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if (res.code === '401') {
            router.push("/login")
        }
        return res;
    },
        error => {
        if (error.response.status === 404) {
            ElMessage.error('API endpoint not found')
        } else if (error.response.status === 500) {
            ElMessage.error('System error, please check the backend console for details')
        } else {
            console.error(error.message)
        }
        return Promise.reject(error)
    }
)


export default request
