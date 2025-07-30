package br.com.ronaldoamorim.wallet_challenge.transaction;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<Transaction, UUID> {

    List<Transaction> findAllBy(Pageable pageable);

}
