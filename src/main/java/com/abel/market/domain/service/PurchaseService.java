package com.abel.market.domain.service;

import com.abel.market.domain.Product;
import com.abel.market.domain.Purchase;
import com.abel.market.domain.repository.ProductRepository;
import com.abel.market.domain.repository.PurchaseRepository;
import com.abel.market.persistence.entity.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> getAll() {
        return purchaseRepository.getAll();
    }

    public Optional<List<Purchase>> getByClient(String clientId) {
        return purchaseRepository.getByClient(clientId);
    }

    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }


}
