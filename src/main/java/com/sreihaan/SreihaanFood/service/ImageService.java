package com.sreihaan.SreihaanFood.service;


import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    public Image saveImage(MultipartFile imageFile, Product product) throws IOException;

    public Image getImage(Product product);

    void deleteImage(long id);
}
