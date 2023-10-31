package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Emotion;
import com.project.bookreport.domain.MyBook;
import com.project.bookreport.exception.custom_exceptions.BookException;
import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.book.BookDetailDTO;
import com.project.bookreport.model.book.BookDetailResponse;
import com.project.bookreport.model.book.BookDetailResponse.Item;
import com.project.bookreport.model.book.BookRequest;
import com.project.bookreport.model.book.BookSearchDTO;
import com.project.bookreport.repository.BookRepository;
import com.project.bookreport.repository.MyBookRepository;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final MyBookRepository myBookRepository;
    private final EmotionService emotionService;

    @Value("${book.searchUrl}")
    private String BOOK_SEARCH_URL;

    @Value("${book.detailUrl}")
    private String BOOK_DETAIL_URL;

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
            Emotion emotion = emotionService.create();
            Book book = Book.builder()
                .isbn(bookRequest.getIsbn())
                .bookName(bookRequest.getBookName())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .description(bookRequest.getDescription())
                .imageUrl(bookRequest.getImageUrl())
                .emotion(emotion)
                .build();
            saveBook = bookRepository.save(book);
        } else {
            saveBook = originBook.get();
        }
        return saveBook;
    }

    /**
     * 책 수정
     */
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

    /**
     * 책 삭제
     */
    public void delete(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new BookException(BOOK_NOT_FOUND));
        List<MyBook> myBooks = myBookRepository.findAllByBook(book);
        if (myBooks.isEmpty()) {
            bookRepository.delete(book);
        }
    }

    /**
     * 책 조회 (id)
     */
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

    public BookDetailDTO detailSearch(String isbn) {
        RestTemplate restTemplate = new RestTemplate();

        URI targetUrl = UriComponentsBuilder
            .fromUriString(BOOK_DETAIL_URL)
            .queryParam("d_isbn", isbn)
            .queryParam("display", 1)
            .build()
            .encode(StandardCharsets.UTF_8)
            .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", ID);
        headers.set("X-Naver-Client-Secret", SECRET);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        try {
            String xmlBody = restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity,
                String.class).getBody();
            ObjectMapper objectMapper = new XmlMapper();
            BookDetailResponse bookDetailResponse = objectMapper.readValue(xmlBody,
                BookDetailResponse.class);
            Item item = bookDetailResponse.getChannel().getItems().get(0);
            return BookDetailDTO.builder()
                .title(item.getTitle())
                .image(item.getImage())
                .author(item.getAuthor())
                .isbn(item.getIsbn())
                .publisher(item.getPublisher())
                .description(item.getDescription())
                .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BookException(BOOK_SEARCH_FAIL);
        }
    }
}

