import api from './axiosInstance'

export type BookResponse = {
    id: number;
    title: string;
    author: string;
    isAvailableForBorrow: boolean;
}

/**
 * 도서 검색 요청을 백엔드로 보냅니다.
 * @param keyword 검색 키워드
 * @returns 검색된 도서 목록
 */
export async function searchBooks(keyword: string) {
    const { data } = await api.get<BookResponse[]>('/books', { params: { keyword } })
    return data
}

/**
 * 도서 대출 요청을 백엔드로 보냅니다.
 * @param bookId 대출할 도서 ID
 * @returns void
 */
export async function borrowBook(bookId: number) {
    await api.post<void>('/borrow', { bookId })
}