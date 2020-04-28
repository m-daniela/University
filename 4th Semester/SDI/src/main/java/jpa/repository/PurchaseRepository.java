package jpa.repository;

import jpa.domain.Purchase;

import java.util.List;

public interface PurchaseRepository extends GeneralRepository<Purchase, Long> {
    List<Purchase> findPurchasesByClientID(Long clientID);
    List<Purchase> findPurchasesByBookID(Long bookID);

}
