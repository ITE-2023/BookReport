package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.*;
import com.project.bookreport.domain.Book;
import com.project.bookreport.exception.custom_exceptions.BookException;
import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.book.BookRequest;
import com.project.bookreport.model.book.BookSearchDTO;
import com.project.bookreport.repository.BookRepository;
import com.project.bookreport.repository.MyBookRepository;
import com.project.bookreport.repository.MemberRepository;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final MyBookRepository myBookRepository;

    @Value("${book.searchUrl}")
    private String BOOK_SEARCH_URL;

    @Value("${book.id}")
    private String ID;

    @Value("${book.secret}")
    private String SECRET;

    /**
     * 책 생성
     * - 기존에 생성된 책이라면, 그대로 return
     * - 기존에 생성되지 않았다면, 생성 후 return
     */
    public BookDTO create(BookRequest bookRequest){
        Book saveBook = getSaveBook(bookRequest);
        return BookDTO.builder()
            .id(saveBook.getId())
            .isbn(saveBook.getIsbn())
            .bookName(saveBook.getBookName())
            .author(saveBook.getAuthor())
            .publisher(saveBook.getPublisher())
            .description(saveBook.getDescription())
            .imageUrl(saveBook.getImageUrl())
            .createDate(saveBook.getCreateDate())
            .updateDate(saveBook.getUpdateDate())
            .build();
    }

    private Book getSaveBook(BookRequest bookRequest) {
        Optional<Book> originBook = getBook(bookRequest.getIsbn());
        Book saveBook;
        if (originBook.isEmpty()) {
            Book book = Book.builder()
                .isbn(bookRequest.getIsbn())
                .bookName(bookRequest.getBookName())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .description(bookRequest.getDescription())
                .imageUrl(bookRequest.getImageUrl())
                .build();
            saveBook = bookRepository.save(book);
        } else {
            saveBook = originBook.get();
        }
        return saveBook;
    }

    public void delete(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new BookException(BOOK_NOT_FOUND));
        bookRepository.delete(book);
    }
    @Transactional
    public BookDTO update(BookRequest bookRequest, Long id){
        Book book = findBookById(id);
        book.setIsbn(bookRequest.getIsbn());
        book.setBookName(bookRequest.getBookName());
        book.setAuthor(bookRequest.getAuthor());
        book.setPublisher(bookRequest.getPublisher());
        book.setDescription(bookRequest.getDescription());
        book.setImageUrl(bookRequest.getImageUrl());
        return BookDTO.builder()
            .id(book.getId())
            .isbn(book.getIsbn())
            .bookName(book.getBookName())
            .author(book.getAuthor())
            .publisher(book.getPublisher())
            .description(book.getDescription())
            .imageUrl(book.getImageUrl())
            .createDate(book.getCreateDate())
            .updateDate(book.getUpdateDate())
            .build();

    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(()-> new BookException(BOOK_NOT_FOUND));
    }

    /**
     * isbn이 일치하는 책 조회
     */
    private Optional<Book> getBook(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    /**
     * 네이버 API - 책 검색
     */
    public BookSearchDTO search(String keyword) {
        RestTemplate restTemplate = new RestTemplate();

        URI targetUrl = UriComponentsBuilder
            .fromUriString(BOOK_SEARCH_URL)
            .queryParam("query", keyword)
            .queryParam("display", 10)
            .build()
            .encode(StandardCharsets.UTF_8)
            .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", ID);
        headers.set("X-Naver-Client-Secret", SECRET);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, BookSearchDTO.class)
                .getBody();
        } catch (Exception e) {
            throw new BookException(BOOK_SEARCH_FAIL);
        }
    }
}

