package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.exception.ImageNotFoundException;
import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.repository.ImageRepository;
import com.sreihaan.SreihaanFood.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image saveImage(MultipartFile imageFile, Product product) throws IOException {
        if(imageFile == null || imageFile.getSize() <=0)
        {
            return null;
        }
        Long size = imageFile.getSize();
        String filename = imageFile.getOriginalFilename();
        String contentType = imageFile.getContentType();
        byte[] data = compress(imageFile.getBytes());
        Image image = new Image(0, filename, contentType, size, product, data);
        return imageRepository.save(image);
    }

    public Image getImage(Product product)
    {
        Image image = null;
        try
        {
            image = new Image(product.getImage());
            image.setPhoto(decompress(image.getPhoto()));
        }
        catch (Exception ex)
        {
            throw new ImageNotFoundException("Image not found, Product Id -> "+product.getId());
        }
        return image;
    }

    @Override
    public void deleteImage(long id) {
        imageRepository.deleteById(id);
    }

    public static byte[] compress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DeflaterOutputStream defl = new DeflaterOutputStream(out);
            defl.write(in);
            defl.flush();
            defl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decompress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InflaterOutputStream infl = new InflaterOutputStream(out);
            infl.write(in);
            infl.flush();
            infl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
