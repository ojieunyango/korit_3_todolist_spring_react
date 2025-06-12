import axios from "axios";

const apiClint = axios.create({
  baseURL: 'http://localhost:8080/api'
});

apiClint.interceptors.request.use(
  (config)=>{
    const token = localStorage.getItem('authToken');
    if(token){
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error)=>{
    return Promise.reject(error);
  }
);
export default apiClint;