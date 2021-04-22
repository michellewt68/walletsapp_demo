package org.bsim.intern.service.impl;

import org.bsim.intern.io.entity.UserEntity;
import org.bsim.intern.io.entity.WalletEntity;
import org.bsim.intern.io.irepository.UserRepository;
import org.bsim.intern.io.irepository.WalletRepository;
import org.bsim.intern.service.iservice.IWalletsService;
import org.bsim.intern.shared.dto.UserDTO;
import org.bsim.intern.shared.dto.WalletDTO;
import org.bsim.intern.shared.utils.GenerateRandomPublicId;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements IWalletsService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GenerateRandomPublicId generateRandomPublicId;

    @Override
    public List<WalletDTO> getAllWalletsData(String userid) {
        List<WalletEntity>walletsData=walletRepository.findAllByUser(userRepository.findByUserid(userid));

        //pindahin data dri list ke list
        return new ModelMapper().map(walletsData,new TypeToken<List<WalletDTO>>(){}.getType());
    }

    @Override
    public long getTotalBalance(String userid) {
        List<WalletEntity> walletsData=walletRepository.findAllByUser(userRepository.findByUserid(userid));

        if(walletsData==null){
            return 0L;
        }
        long totalBalance=0;
        for(WalletEntity walletEntity : walletsData){
            totalBalance+=walletEntity.getBalance();
        }

        return totalBalance;
    }

    @Override
    public WalletDTO addNewWalletData(String userid, WalletDTO walletDTO) {

        ModelMapper mapper=new ModelMapper();

        //generate wallet id
        walletDTO.setWalletId(generateRandomPublicId.generateUserId(30));

        //cek apakah user ud ad ato blm (get user)
        UserEntity userData=userRepository.findByUserid(userid);

        //set user
        walletDTO.setUser(mapper.map(userData, UserDTO.class));

        WalletEntity entity=mapper.map(walletDTO, WalletEntity.class);

        //save to db (table: wallettbl)
        WalletEntity storedData= walletRepository.save(entity);

        return mapper.map(storedData, WalletDTO.class);
    }

    @Override
    public WalletDTO updateWalletData(String userid, String walletid, WalletDTO walletDTO) {
        WalletEntity walletData= walletRepository.findByWalletid(walletid);

        if(walletData==null) return null;

        //update nohp/balance
        walletData.setPhonenumber(walletDTO.getPhoneNumber());
        walletData.setBalance(walletDTO.getBalance());

        WalletEntity updateData= walletRepository.save(walletData);

        return new ModelMapper().map(updateData, WalletDTO.class);
    }
}
