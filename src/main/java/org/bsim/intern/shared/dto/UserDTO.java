package org.bsim.intern.shared.dto;

import java.io.Serializable;
import java.util.List;

//jembatan dari client ke service, service ke repository
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 3835024278707944744L;
    private long id;
    private String userId;
    private String userName;
    private List<WalletDTO> listWallet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<WalletDTO> getListWallet() {
        return listWallet;
    }

    public void setListWallet(List<WalletDTO> listWallet) {
        this.listWallet = listWallet;
    }
}
