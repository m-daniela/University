package jpa.repository;

import jpa.domain.Book;

import java.util.List;

public interface BookRepository extends GeneralRepository<Book, Long> {
    List<Book> findBookByTitleLike(String title);
}
