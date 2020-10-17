package core.service;


import core.model.Book;
import core.model.Purchase;
import core.model.validators.PurchaseValidator;
import core.model.validators.ValidatorException;
import core.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PurchaseService {
    private static final Logger log = LoggerFactory.getLogger(PurchaseService.class);
    @Autowired
    private PurchaseRepository repository;
    @Autowired
    private BookService repositoryBook;
    @Autowired
    private PurchaseValidator purchaseValidator;



    public Purchase addPurchase(Purchase purchase) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        log.trace("PurchaseService: addPurchase({})", purchase);
        updateBook(purchase.getBookID(), purchase.getNrBooks(), 0);
        purchaseValidator.validate(purchase);
        return repository.save(purchase);
    }

    public void removePurchase(Long ID) throws SQLException {
        log.trace("PurchaseService: removePurchase({})", ID);
        Optional<Purchase> purchase = findOnePurchase(ID);

        purchase.ifPresent(p->{
            try {
                updateBook(p.getBookID(), 0, p.getNrBooks());
                repository.deleteById(ID);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            log.trace("PurchaseService: purchase removed {}", p);

        });
    }

    @Transactional
    public void updatePurchase(Purchase purchase) throws ValidatorException, SQLException {
        log.trace("PurchaseService: updatePurchase({})", purchase);
        purchaseValidator.validate(purchase);
        Optional<Purchase> newpurchase = findOnePurchase(purchase.getId());
        newpurchase.ifPresent(p->{
            p.setClientID(purchase.getClientID());
            p.setBookID(purchase.getBookID());
            try {
                updateBook(purchase.getBookID(), purchase.getNrBooks(), p.getNrBooks());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            p.setNrBooks(purchase.getNrBooks());
            log.trace("PurchaseService: purchase updated {}", p);

        });

    }

    public List<Purchase> getAllPurchases() throws SQLException {
        log.trace("PurchaseService: getAllPurchases()");

        return repository.findAll();
    }

    public List<Purchase> getAllPurchases(Sort sort) throws SQLException {
        log.trace("PurchaseService: getAll({})", sort);
        return repository.findAll(sort);

    }

    public List<Purchase> filterPurchasesByClientID(Long clientID) throws SQLException {
        log.trace("PurchaseService: filterPurchasesbyClientID({})", clientID);
        return repository.findPurchasesByClientID(clientID);
    }

    public Optional<Purchase> findOnePurchase(Long purchaseID) throws SQLException {
        log.trace("PurchaseService: findOnePurchase({})", purchaseID);
        return repository.findById(purchaseID);
    }

    @Transactional
    void updateBook(Long bookID, int newNrBooks, int oldNrBooks) throws SQLException {
        log.trace("PurchaseService: updateBook({}, {}, {})", bookID, newNrBooks, oldNrBooks);

        Optional<Book> book = repositoryBook.findOneBook(bookID);
        book.ifPresent(b->{
            b.setInStock(b.getInStock() - (newNrBooks - oldNrBooks));
            try {
                repositoryBook.update(b);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            log.trace("PurchaseService: book updated {}", b);

        });
    }
}
