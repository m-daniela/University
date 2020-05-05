package core.service;


import core.model.Book;
import core.model.Purchase;
import core.model.validators.BookValidator;
import core.model.validators.ValidatorException;
import core.repository.BookRepository;
import core.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookRepository repository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private BookValidator bookValidator;

    public Book add(Book book) throws ValidatorException {
        // Add given book to the jpa.repository
        // Return Optional null if the entity was added, otherwise return the entity with the same id
        log.trace("BookService: add({})", book);
        bookValidator.validate(book);
        return repository.save(book);
    }

    public List<Book> getAll() throws SQLException {
        // Return all books from the jpa.repository
        log.trace("BookService: getAll()");
        return repository.findAll();
    }

    public List<Book> getAll(Sort sort) throws SQLException {
        log.trace("BookService: getAll({})", sort);
        return repository.findAll(sort);
    }

    public List<Book> filter(String s) throws SQLException {
        // Return the books that contain the given string
        log.trace("BookService: filter({})", s);
        return repository.findBookByTitleLike(s);
    }

    @Transactional
    public void update(Book book) throws SQLException {
        // Update the book with the same id as the one given
        // Return Optional null if the entity was updated, otherwise the given entity (if the id does not exist)
        log.trace("BookService: update({})", book);
        Optional<Book> newbook = findOneBook(book.getId());
        bookValidator.validate(book);
        newbook.ifPresent(b->{
            b.setTitle(book.getTitle());
            b.setAuthor(book.getAuthor());
            b.setYear(book.getYear());
            b.setPrice(book.getPrice());
            b.setInStock(book.getInStock());
            log.trace("BookService: book updated {}", b);

        });
    }


    public void delete(Long bookID) throws ValidatorException, SQLException {
        // Delete the book with the given id from jpa.repository
        // Return Optional null if the entity was deleted, otherwise return the entity with the same id
        log.trace("BookService: delete({})", bookID);
        findOneBook(bookID).ifPresent(b->{
            List<Purchase> purchases = purchaseRepository.findPurchasesByBookID(bookID);
            purchases.forEach(purchase -> {purchaseRepository.deleteById(purchase.getId());});
            repository.deleteById(bookID);
            log.trace("BookService: book deleted {}", b);
        });

    }

    public Optional<Book> findOneBook(Long bookID) throws SQLException {
        log.trace("BookService: findOneBook({})", bookID);

        return repository.findById(bookID);
    }

}


