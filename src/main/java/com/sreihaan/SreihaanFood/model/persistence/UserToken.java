package com.sreihaan.SreihaanFood.model.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    @OneToOne( cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "tokenId", referencedColumnName = "id")
    private User user;

    private Date expiryDate;

    public UserToken(){};

    public UserToken(UserToken userToken) {
        this.id = userToken.getId();
        this.token = userToken.getToken();
        this.user = userToken.getUser();
        this.expiryDate = userToken.getExpiryDate();
    }
}
