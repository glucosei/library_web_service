import { useState } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'

export default function LoginPage() {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const { doLogin, loading, error } = useAuth()
    const navigate = useNavigate()
    const [params] = useSearchParams()

    const handleLogin = async () => {
        const ok = await doLogin({ username, password })
        if (ok) {
            const next = params.get('next')
            navigate(next || '/search', { replace: true })
        }
    }

    return (
        <main className="container">
            <h2>로그인</h2>
            <div className="row">
                <input
                    placeholder="아이디"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
            </div>
            <div className="row">
                <input
                    type="password"
                    placeholder="비밀번호"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>
            <div className="row">
                <button onClick={handleLogin} disabled={loading}>
                    {loading ? '로그인 중...' : '로그인'}
                </button>
            </div>
            {error && <div style={{ color: 'crimson' }}>{error}</div>}

            {/* 회원가입 버튼 추가 부분 */}
            <div className="row">
                <a onClick={() => navigate('/signup')}>회원가입</a>
            </div>
        </main>
    )
}