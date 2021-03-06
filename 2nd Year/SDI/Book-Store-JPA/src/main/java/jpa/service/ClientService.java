package jpa.service;

import jpa.domain.Client;
import jpa.domain.Purchase;
import jpa.domain.validators.ClientValidator;
import jpa.domain.validators.ValidatorException;
import jpa.repository.ClientRepository;
import jpa.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    private ClientRepository repository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ClientValidator clientValidator;

    public Client addClient(Client client) throws ValidatorException {
        log.trace("ClientService: addClient({})", client);
        clientValidator.validate(client);
        return repository.save(client);
    }

    public List<Client> getAllClients(Sort sort) throws SQLException {
        log.trace("ClientService: getAll({})", sort);
        return repository.findAll(sort);

    }

    public void removeClient(Long ID) throws SQLException {
        log.trace("ClientService: removeClient({})", ID);
        findOneClient(ID).ifPresent(c->{
            List<Purchase> purchases = purchaseRepository.findPurchasesByClientID(ID);
            purchases.forEach(purchase -> {purchaseRepository.deleteById(purchase.getId());});
            repository.deleteById(ID);
            log.trace("ClientService: client deleted {}", c);

        });

    }

    @Transactional
    public void updateClient(jpa.domain.Client client) throws ValidatorException, SQLException {
        log.trace("ClientService: updateClient({})", client);
        Optional<Client> newclient = findOneClient(client.getId());
        clientValidator.validate(client);
        newclient.ifPresent(c->{
            c.setName(client.getName());
            log.debug("ClientService: client updated {}", c);

        });
    }

    public List<Client> getAllClients() throws SQLException {
        log.trace("ClientService: getAllClients()");
        return repository.findAll();
    }

    public List<jpa.domain.Client> filterClientsByName(String s) throws SQLException {
        log.trace("ClientService: filterClientByName({})", s);
        return repository.findClientsByNameLike(s);
    }

    public Optional<Client> findOneClient(Long clientID) throws SQLException {
        log.trace("ClientService: findOneClient({})", clientID);
        return repository.findById(clientID);
    }
}
