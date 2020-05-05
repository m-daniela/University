package web.converter;

import core.model.Book;
import org.springframework.stereotype.Component;
import web.dto.book.BookDto;

@Component
public class BookConverter extends BaseConverter<Book, BookDto> {
    @Override
    public Book convertDtoToModel(BookDto dto) {
        Book book = Book.builder().serialNumber(dto.getSerialNumber())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .year(dto.getYear())
                .price(dto.getPrice())
                .inStock(dto.getInStock())
                .build();
        book.setId(dto.getId());
        return book;
    }

    @Override
    public BookDto convertModelToDto(Book book) {
        BookDto dto = BookDto.builder()
                .serialNumber(book.getSerialNumber())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .price(book.getPrice())
                .inStock(book.getInStock())
                .build();
        dto.setId(book.getId());
        return dto;

    }
}
