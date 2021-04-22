package org.bsim.intern.service.impl;

import org.bsim.intern.io.entity.TransactionEntity;
import org.bsim.intern.io.entity.WalletEntity;
import org.bsim.intern.io.irepository.TransactionRepository;
import org.bsim.intern.io.irepository.WalletRepository;
import org.bsim.intern.service.iservice.IServiceTransaction;
import org.bsim.intern.shared.dto.TransactionDTO;
import org.bsim.intern.shared.utils.GenerateRandomPublicId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements IServiceTransaction {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final GenerateRandomPublicId generateRandomPublicId;

    public TransactionServiceImpl(TransactionRepository transactionRepository, WalletRepository walletRepository, GenerateRandomPublicId generateRandomPublicId) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.generateRandomPublicId = generateRandomPublicId;
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {

        ModelMapper mapper=new ModelMapper();
        //wadah utk map dari entity ke dto
        List<TransactionDTO> value= new ArrayList<>();

        List<TransactionEntity> transactionEntities= transactionRepository.findAll();
        for(TransactionEntity transactionEntity:transactionEntities){
            value.add(mapper.map(transactionEntity, TransactionDTO.class));
        }

        return value;
    }

    @Override
    public TransactionDTO addNewTransaction(String walletid, TransactionDTO transactionDTO) {
        ModelMapper mapper=new ModelMapper();

        TransactionEntity entity=mapper.map(transactionDTO, TransactionEntity.class);
        WalletEntity walletEntity= walletRepository.findByWalletid(walletid);

        entity.setWalletEntity(walletEntity);
        entity.setTransactionid(generateRandomPublicId.generateUserId(35));
        TransactionEntity storedData= transactionRepository.save(entity);

        TransactionDTO value=mapper.map(storedData, TransactionDTO.class);

        return value;
    }

    @Override
    public List<TransactionDTO> getAllTransactionsByWalletid(String walletid) {
        ModelMapper mapper=new ModelMapper();
        List<TransactionDTO> value=new ArrayList<>();
        WalletEntity walletEntity=walletRepository.findByWalletid(walletid);

        List<TransactionEntity> transactionEntities= transactionRepository.findAllByWalletEntity(walletEntity);

        for(TransactionEntity entity:transactionEntities){
            value.add(mapper.map(entity, TransactionDTO.class));
        }
        return value;
    }

    @Override
    public TransactionDTO updateTransactionByTransactionid(String walletid, String transactionid, TransactionDTO transactionDTO) {

        long balanceAwal=0;
        long amountAwal=0;
        long amountUpdate= transactionDTO.getAmount();


        ModelMapper mapper=new ModelMapper();
        TransactionEntity transactionEntity= transactionRepository.findByTransactionid(transactionid);
        amountAwal=transactionEntity.getAmount();
        WalletEntity walletEntity=walletRepository.findByWalletid(walletid);
        balanceAwal=walletEntity.getBalance();
        if(amountAwal-amountUpdate>0) {
            walletEntity.setBalance(balanceAwal+(amountUpdate-amountAwal));
        }else{
            walletEntity.setBalance(balanceAwal-(amountUpdate-amountAwal));
        }
//      if(transactionEntity==null||walletEntity==null) return null;

        TransactionEntity entity=mapper.map(transactionDTO, TransactionEntity.class);
        entity.setWalletEntity(walletEntity);
        entity.setId(transactionEntity.getId());
        entity.setTransactionid(transactionEntity.getTransactionid());

        TransactionEntity storedData= transactionRepository.save(entity);
        return mapper.map(storedData, TransactionDTO.class);
    }

    @Override
    public TransactionDTO deleteTransaction(String walletid, String transactionid) {
        ModelMapper mapper=new ModelMapper();
        WalletEntity walletEntity= walletRepository.findByWalletid(walletid);

        TransactionEntity transactionEntity= transactionRepository.findByTransactionid(transactionid);
        transactionEntity.setWalletEntity(walletEntity);
        transactionEntity.setDeleted(true);

        TransactionEntity storedData= transactionRepository.save(transactionEntity);

        return mapper.map(storedData, TransactionDTO.class);
    }
}
