import axios from 'axios'


const instance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE || '/api',
    withCredentials: false,
})


// 요청 인터셉터: 토큰 자동 주입
instance.interceptors.request.use((config) => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers = config.headers || {}
        config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
})


// 응답 인터셉터: 401 시 로그인으로 유도
instance.interceptors.response.use(
    (res) => res,
    (error) => {
        if (error?.response?.status === 401) {
            localStorage.removeItem('token')
// 로그인 페이지로 이동
            if (typeof window !== 'undefined') {
                const here = window.location.pathname
                const next = here && here !== '/login' ? `?next=${encodeURIComponent(here)}` : ''
                window.location.href = `/login${next}`
            }
        }
        return Promise.reject(error)
    }
)


export default instance