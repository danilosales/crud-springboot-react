import axios from 'axios';

const API_URL = 'http://localhost:8080';
const USERNAME_TOKEN = "userToken";
const PERMISSION_WRITE = "permissionWrite";

class ApiService {

  constructor() {
    this.api = axios.create({
      baseURL: API_URL
    })
    this.api.defaults.headers.common = {};
    this.registerInterceptors();
  }

  registerInterceptors() {
    this.api.interceptors.request.use(async config => {
      const token = sessionStorage.getItem(USERNAME_TOKEN);
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    });
  }

  login(username, password) {
    return this.api.post(`/auth/login`, {
        username,
        password
    })
  }

  registerSuccesfullLogin(username, data) {
    sessionStorage.setItem(USERNAME_TOKEN, data.token)
    sessionStorage.setItem(PERMISSION_WRITE, data.admin)
    this.setupAxiosInterceptors(this.createJWTToken(data.token))
  }

  createJWTToken(token) {
    return 'Bearer ' + token
  }


  logout() {
    sessionStorage.removeItem(USERNAME_TOKEN);
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(USERNAME_TOKEN)
    if (user === null) return false
    return true
  }

  isUserAdmin() {
    let permission = sessionStorage.getItem(PERMISSION_WRITE)
    if (permission === null) return false
    return permission === 'true';
  }

  setupAxiosInterceptors(token) {
    this.api.interceptors.request.use(
        (config) => {
            if (this.isUserLoggedIn()) {
                config.headers.Authorization = token
            }
            return config
        }
    )
  }

  getTodosClientes() {
    this.registerInterceptors();
    return this.api.get(`${API_URL}/api/clientes`);
  }

  getClientePorId(id) {
    return this.api.get(`${API_URL}/api/clientes/${id}`);
  }

  excluirClientePorId(id) {
    return this.api.delete(`${API_URL}/api/clientes/${id}`);
  }  

  cadastrarNovoCliente(data) {
    return this.api.post(`${API_URL}/api/clientes`, data);
  }

  atualizarCliente(id, data) {
    return this.api.put(`${API_URL}/api/clientes/${id}`, data);
  }

}

export default new ApiService();