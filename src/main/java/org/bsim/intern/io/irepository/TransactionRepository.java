package org.bsim.intern.io.irepository;

import org.bsim.intern.io.entity.TransactionEntity;
import org.bsim.intern.io.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByWalletEntity(WalletEntity walletEntity);

    TransactionEntity findByTransactionid(String transactionid);
}
