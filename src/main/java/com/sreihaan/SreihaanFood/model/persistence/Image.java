package com.sreihaan.SreihaanFood.model.persistence;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String filename;

    private String contentType;

    private long fileSize;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo;

    public Image(Image image)
    {
        this.id = image.getId();
        this.filename = image.getFilename();
        this.fileSize = image.getFileSize();
        this.contentType = image.getContentType();
        this.photo = image.getPhoto();
    }

}
