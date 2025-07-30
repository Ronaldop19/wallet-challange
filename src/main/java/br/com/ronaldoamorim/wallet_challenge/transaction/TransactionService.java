package br.com.ronaldoamorim.wallet_challenge.transaction;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.ronaldoamorim.wallet_challenge.Authorization.AuthorizationService;
import br.com.ronaldoamorim.wallet_challenge.Notification.NotificationService;
import br.com.ronaldoamorim.wallet_challenge.wallet.WalletRepository;
import br.com.ronaldoamorim.wallet_challenge.wallet.WalletType;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository, AuthorizationService AuthorizationService,
    NotificationService NotificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizationService = AuthorizationService;
        this.notificationService = NotificationService;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        validate(transaction);

        var  newTransaction = transactionRepository.save(transaction);

        var walletPayer = walletRepository.findById(transaction.payer()).get();
        var walletPayee = walletRepository.findById(transaction.payee()).get();

        walletRepository.save(walletPayer.debit(transaction.value()));
        walletRepository.save(walletPayee.credit(transaction.value()));

        authorizationService.authorize(transaction);

        notificationService.notify(transaction);

        return newTransaction;
    }

    private void validate (Transaction transaction) {
        walletRepository.findById(transaction.payee())
        .map(payee -> walletRepository.findById(transaction.payer())
            .map(payer -> payer.type() == WalletType.CUSTOMER.getValue() &&
                    payer.balance().compareTo(transaction.value()) >= 0 &&
                    !payer.id().equals(transaction.payee()) ? true : null)).orElseThrow(() -> new TransactionException("Invalid transaction: " + transaction))
                    .orElseThrow(() -> new TransactionException("Invalid transaction: " + transaction));
    }

    public Map<String, Object> list(int page, int size, String sortBy, String sortDirection) {
        // page: número da página (começa em 0)
        // size: quantidade de itens por página
        // sortBy: campo para ordenar (ex: "createdAt", "value")
        // sortDirection: "asc" para ascendente, "desc" para descendente

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Transaction> transactions = transactionRepository.findAllBy(pageable); 

        long totalTransactions = transactionRepository.count();
        long totalPages = (long) Math.ceil((double) totalTransactions / size);

        return Map.of(
            "content", transactions,
            "page", page,
            "size", size,
            "totalElements", totalTransactions,
            "totalPages", totalPages
        );
    }
}
 