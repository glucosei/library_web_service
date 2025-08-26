import axios from './axiosInstance'
import api from './axiosInstance';


export type LoginRequest = { username: string; password: string }
export type TokenResponse = { token: string }
export type SignUpRequest = {
    username: string;
    password: string;
    name: string;
    nickname: string;
    phoneNumber: string;
    gender: 'MALE' | 'FEMALE';
}

export async function login(payload: LoginRequest) {
    const { data } = await axios.post<TokenResponse>('/auth/login', payload)
    return data
}


export async function signup(payload: SignUpRequest) {
    await api.post<void>('/auth/signup', payload);
}