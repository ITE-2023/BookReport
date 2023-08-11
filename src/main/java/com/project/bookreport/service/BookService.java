package com.project.bookreport.service;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Report;
import com.project.bookreport.exception.BookException;
import com.project.bookreport.exception.ErrorCode;
import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.book.BookRequest;
import com.project.bookreport.repository.BookRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookDTO create(BookRequest bookRequest){
        Optional<Book> originBook = getBook(bookRequest);
        Book saveBook;
        if (originBook.isEmpty()) {
            Book book = Book.builder()
                .bookName(bookRequest.getBookName())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .build();
            saveBook = bookRepository.save(book);
        } else {
            saveBook = originBook.get();
        }
        return BookDTO.builder()
            .id(saveBook.getId())
            .bookName(saveBook.getBookName())
            .author(saveBook.getAuthor())
            .publisher(saveBook.getPublisher())
            .createDate(saveBook.getCreateDate())
            .updateDate(saveBook.getUpdateDate())
            .build();
    }

    private Optional<Book> getBook(BookRequest bookRequest) {
        return bookRepository.findByBookNameAndAuthorAndPublisher(
            bookRequest.getBookName(),
            bookRequest.getAuthor(), bookRequest.getPublisher());
    }

    public BookDTO update(Report report, BookRequest bookRequest){
        if(!report.getBook().getBookName().equals(bookRequest.getBookName())){
            throw new BookException(ErrorCode.ACCESS_DENIED);
        }
        Book book = report.getBook();
        book.setBookName(bookRequest.getBookName());
        book.setAuthor(bookRequest.getAuthor());
        book.setPublisher(bookRequest.getPublisher());
        return BookDTO.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }
}

