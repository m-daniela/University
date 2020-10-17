package core.repository;


import core.model.Purchase;

import java.util.List;

public interface PurchaseRepository extends GeneralRepository<Purchase, Long> {
    List<Purchase> findPurchasesByClientID(Long clientID);
    List<Purchase> findPurchasesByBookID(Long bookID);

}
