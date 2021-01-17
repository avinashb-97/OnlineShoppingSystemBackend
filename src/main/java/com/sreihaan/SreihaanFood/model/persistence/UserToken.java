package com.sreihaan.SreihaanFood.model.persistence;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@Document(collection = "user_token")
public class UserToken {

    @Id
    private long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinProperty(name = "userToken")
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
