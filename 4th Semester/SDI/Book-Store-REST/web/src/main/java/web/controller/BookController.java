package web.controller;

import core.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.BookConverter;
import web.dto.book.BookDto;
import web.dto.book.BooksDto;

import java.sql.SQLException;

@RestController
public class BookController {
    public static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookConverter bookConverter;
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    BookDto add(@RequestBody BookDto bookDto){
        log.trace("BookController: add({})", bookDto);
        return bookConverter.convertModelToDto(bookService.add(bookConverter.convertDtoToModel(bookDto)));
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    BooksDto getAll() throws SQLException {
        log.debug("BookController: getAll()");
        return new BooksDto(bookConverter.convertModelsToDtos(bookService.getAll()));
    }

    @RequestMapping(value = "/books/{filters}/{direction}", method = RequestMethod.GET)
    BooksDto getAll(@PathVariable String filters, @PathVariable String direction) throws SQLException {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), filters.split(" "));
        log.trace("BookController: getAll({})", sort);
        return new BooksDto(bookConverter.convertModelsToDtos(bookService.getAll(sort)));
    }


    @RequestMapping(value = "/books", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> update(@RequestBody BookDto bookDto) throws SQLException {
        log.trace("BookController: update({})", bookDto);
        bookService.update(bookConverter.convertDtoToModel(bookDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
        log.trace("BookController: delete({})", id);
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/books/filter/{title}", method = RequestMethod.GET)
    BooksDto filter(@PathVariable String title) throws SQLException {
        log.trace("BookController: filter({})", title);
        return new BooksDto(bookConverter.convertModelsToDtos(bookService.filter(title)));
    }
}
