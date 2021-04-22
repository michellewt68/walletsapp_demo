package org.bsim.intern.service.iservice;

import org.bsim.intern.shared.dto.TransactionDTO;

import java.util.List;

public interface IServiceTransaction {
    List<TransactionDTO> getAllTransactions();

    TransactionDTO addNewTransaction(String walletid, TransactionDTO transactionDTO);

    List<TransactionDTO> getAllTransactionsByWalletid(String walletid);

    TransactionDTO updateTransactionByTransactionid(String walletid, String transactionid, TransactionDTO transactionDTO);

    TransactionDTO deleteTransaction(String walletid, String transactionid);
}
