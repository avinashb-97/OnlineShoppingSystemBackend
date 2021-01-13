package com.sreihaan.SreihaanFood.model.persistence;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "image")
public class Image {

    @Id
    private long id;

    private String filename;

    private String contentType;

    private long fileSize;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinProperty(name = "product")
    private Product product;

    private byte[] data;

}
