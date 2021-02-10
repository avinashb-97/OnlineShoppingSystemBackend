package com.sreihaan.SreihaanFood.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="image_entity_seq_gen", sequenceName="IMAGE_ENTITY_SEQ")
    private long id;

    private String filename;

    private String contentType;

    private long fileSize;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Product product;

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo;

}
