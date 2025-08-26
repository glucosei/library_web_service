import { useState } from 'react'
import { useAuth } from '../hooks/useAuth'
import { searchBooks, borrowBook } from '../api/books'
import type { BookResponse } from '../api/books'

export default function SearchPage() {
    const [searchQuery, setSearchQuery] = useState('')
    const [books, setBooks] = useState<BookResponse[]>([]);
    const [message, setMessage] = useState('')
    const { logout } = useAuth()

    const handleSearch = async () => {
        setMessage('검색 중입니다...')
        try {
            const results = await searchBooks(searchQuery)
            setBooks(results)
            setMessage(results.length > 0 ? '' : '검색 결과가 없습니다.')
        } catch (error: any) {
            setMessage('검색에 실패했습니다.')
        }
    }

    const handleBorrow = async (bookId: number) => {
        setMessage('대출을 시도하고 있습니다...')
        try {
            await borrowBook(bookId)
            setMessage('도서 대출에 성공했습니다!')
            // 대출 후 검색 결과를 새로고침하여 대출 버튼을 비활성화
            await handleSearch()
        } catch (error: any) {
            setMessage(error.response?.data?.message || '도서 대출에 실패했습니다.')
        }
    }

    return (
        <main className="container">
            <h2>도서 검색</h2>
            <div className="search-row">
                <input
                    placeholder="제목 또는 저자명"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                />
                <button onClick={handleSearch}>검색</button>
                <button onClick={logout}>로그아웃</button>
            </div>

            {message && <p className="message">{message}</p>}

            {books.length > 0 && (
                <div className="book-list">
                    {books.map(book => (
                        <div key={book.id} className="book-item">
                            <div>
                                <h3>{book.title}</h3>
                                <p>저자: {book.author}</p>
                            </div>
                            {book.isAvailableForBorrow ? (
                                <button className="borrow-button" onClick={() => handleBorrow(book.id)}>
                                    대출
                                </button>
                            ) : (
                                <button className="borrow-button" disabled>대출 불가</button>
                            )}
                        </div>
                    ))}
                </div>
            )}
        </main>
    )
}