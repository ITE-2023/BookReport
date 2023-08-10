package com.project.bookreport.service;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Report;
import com.project.bookreport.exception.BookException;
import com.project.bookreport.exception.ErrorCode;
import com.project.bookreport.model.book.BookCreateRequest;
import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.book.BookUpdateRequest;
import com.project.bookreport.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookDTO create(BookCreateRequest bookCreateRequest){
        Book book = Book.builder()
                .bookName(bookCreateRequest.getBookName())
                .author(bookCreateRequest.getAuthor())
                .publisher(bookCreateRequest.getPublisher())
                .build();
        Book savedBook = bookRepository.save(book);
        return BookDTO.builder()
            .id(savedBook.getId())
            .bookName(savedBook.getBookName())
            .author(savedBook.getAuthor())
            .publisher(savedBook.getPublisher())
            .createDate(savedBook.getCreateDate())
            .updateDate(savedBook.getUpdateDate())
            .build();
    }
    /*public void delete(BookCreateRequest bookCreateRequest){
        Book book = Book.builder()
                .bookName(bookCreateRequest.getBookName())
                .author(bookCreateRequest.getAuthor())
                .publisher(bookCreateRequest.getPublisher())
                .build();

        List<Book> bookList = bookRepository.findById();
        if(bookList.size() == 0) {

        }
    }*/

    public BookDTO update(Report report, BookUpdateRequest bookUpdateRequest){
        if(!report.getBook().getBookName().equals(bookUpdateRequest.getBookName())){
            throw new BookException(ErrorCode.ACCESS_DENIED);
        }
        Book book = report.getBook();
        book.setBookName(bookUpdateRequest.getBookName());
        book.setAuthor(bookUpdateRequest.getAuthor());
        book.setPublisher(bookUpdateRequest.getPublisher());
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

