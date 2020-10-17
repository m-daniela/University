package jpa.domain.validators;

import jpa.domain.Book;
import jpa.domain.Client;
import jpa.domain.Purchase;
import jpa.service.BookService;
import jpa.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Optional;

@Component
public class PurchaseValidator implements Validator<Purchase> {

    @Autowired
    private ClientService clients;
    @Autowired
    private BookService books;

    private boolean clientExists(Long ID) throws SQLException {
        Optional<Client> client = clients.findOneClient(ID);
        return client.isPresent();
    }

    private boolean bookExists(Long ID) throws SQLException {
        Optional<Book> book = books.findOneBook(ID);
        return book.isPresent();
    }

    private boolean isBookInSock(Long ID, int nrBooks) throws SQLException {
        if (nrBooks==0)
            return false;
        Optional<Book> book = books.findOneBook(ID);
        return book.get().getInStock()>=nrBooks;
    }

    @Override
    public void validate(jpa.domain.Purchase entity) throws ValidatorException {

        Optional<Purchase> purchase = Optional.ofNullable(Optional.ofNullable(entity).orElseThrow(()-> new ValidatorException("Entity is null.")));
        purchase.ifPresent(p->{
            try {
                if (!clientExists(entity.getClientID()))
                    throw new ValidatorException("This client does not exist.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (!bookExists(entity.getBookID()))
                    throw new ValidatorException("This book does not exist.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (!isBookInSock(entity.getBookID(), entity.getNrBooks()))
                    throw new ValidatorException("We don't have that many books of this type in stock or you selected 0 books.");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }

}
