import { Routes, Route, Navigate } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import SearchPage from './pages/SearchPage'
import BorrowPage from './pages/BorrowPage'
import SignupPage from './pages/SignupPage'

export default function App() {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/login" replace />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />
            <Route path="/search" element={<SearchPage />} />
            <Route path="/borrow" element={<BorrowPage />} />
            <Route path="*" element={<div>Not Found</div>} />
        </Routes>
    )
}