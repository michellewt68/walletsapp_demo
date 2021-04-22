package org.bsim.intern.service.iservice;

import org.bsim.intern.shared.dto.WalletDTO;

import java.util.List;

public interface IWalletsService {

    List<WalletDTO> getAllWalletsData(String userid);
    long getTotalBalance(String userid);
    WalletDTO addNewWalletData(String userid, WalletDTO walletDTO);
    WalletDTO updateWalletData(String userid, String walletid, WalletDTO walletDTO);
}
