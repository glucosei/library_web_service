import { useCallback, useMemo, useState } from 'react'
import { login, type LoginRequest } from '../api/auth'


export function useAuth() {
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)


    const isAuthenticated = useMemo(() => !!localStorage.getItem('token'), [])


    const doLogin = useCallback(async (payload: LoginRequest) => {
        setLoading(true)
        setError(null)
        try {
            const res = await login(payload)
            localStorage.setItem('token', res.token)
            return true
        } catch (e: any) {
            setError(e?.response?.data?.message || '로그인 실패')
            return false
        } finally {
            setLoading(false)
        }
    }, [])


    const logout = useCallback(() => {
        localStorage.removeItem('token')
        window.location.href = '/login'
    }, [])


    return { loading, error, isAuthenticated, doLogin, logout }
}