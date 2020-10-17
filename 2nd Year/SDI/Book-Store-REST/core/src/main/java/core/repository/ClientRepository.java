package core.repository;


import core.model.Client;

import java.util.List;

public interface ClientRepository extends GeneralRepository<Client, Long> {
    List<Client> findClientsByNameLike(String name);
}
