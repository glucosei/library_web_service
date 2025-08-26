
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { signup } from '../api/auth';
import type { SignUpRequest } from '../api/auth';


export default function SignupPage() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState<SignUpRequest>({
        username: '',
        password: '',
        name: '',
        nickname: '',
        phoneNumber: '',
        gender: 'MALE'
    });
    const [message, setMessage] = useState('');

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setMessage('');
        try {
            await signup(formData);
            setMessage('회원가입이 성공적으로 완료되었습니다! 로그인 페이지로 이동합니다.');
            setTimeout(() => navigate('/login'), 2000);
        } catch (error: any) {
            setMessage(error.response?.data?.message || '회원가입에 실패했습니다. 다시 시도해 주세요.');
        }
    };

    return (
        <div className="signup-container">
            <h1>회원가입</h1>
            <form onSubmit={handleSubmit} className="signup-form">
                <input type="text" name="username" value={formData.username} onChange={handleChange} placeholder="아이디" required />
                <input type="password" name="password" value={formData.password} onChange={handleChange} placeholder="비밀번호" required />
                <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="이름" required />
                <input type="text" name="nickname" value={formData.nickname} onChange={handleChange} placeholder="닉네임" required />
                <input type="text" name="phoneNumber" value={formData.phoneNumber} onChange={handleChange} placeholder="전화번호" required />
                <select name="gender" value={formData.gender} onChange={handleChange}>
                    <option value="MALE">남성</option>
                    <option value="FEMALE">여성</option>
                </select>
                <button type="submit">가입하기</button>
            </form>
            {message && <p className="message">{message}</p>}
            <p className="login-link">
                이미 회원이신가요? <a onClick={() => navigate('/login')}>로그인</a>
            </p>
        </div>
    );
}