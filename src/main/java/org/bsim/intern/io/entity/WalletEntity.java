package org.bsim.intern.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "walletsTBL")
@SequenceGenerator(name = "seqwallets", initialValue = 100, allocationSize = 10)
public class WalletEntity implements Serializable {

    private static final long serialVersionUID = 4598421245292347911L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="seqwallets" )
    private long id;

    @Column(nullable = false)
    private String walletid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long balance;

    @Column(nullable = false)
    private String phonenumber;

    //fetchType.eager --> otomatis data yg berelasi akan di load
    //fetchType.lazy --> akan di load kalo dipanggil
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private UserEntity user;

    @OneToMany(mappedBy = "walletEntity")
    private List<TransactionEntity> transactionEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWalletid() {
        return walletid;
    }

    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<TransactionEntity> getTransactionEntity() {
        return transactionEntity;
    }

    public void setTransactionEntity(List<TransactionEntity> transactionEntity) {
        this.transactionEntity = transactionEntity;
    }
}
