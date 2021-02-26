package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sreihaan.SreihaanFood.service.OTPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPServiceImpl implements OTPService {

    private static final Integer EXPIRE_MINS = 10;
    Logger logger = LoggerFactory.getLogger(OTPServiceImpl.class);

    private LoadingCache<String, Integer> otpCache;

    public OTPServiceImpl(){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public int generateOTP(String email)
    {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        logger.info("OTP Generated, email -> "+email+" OTP : "+otp);
        otpCache.put(email, otp);
        return otp;
    }

    public int getOtp(String email)
    {
        try
        {
            return otpCache.get(email);
        }
        catch (Exception e)
        {
            logger.info("OTP not found, email -> "+email+" Exception : "+e.getMessage());
            return 0;
        }
    }

    //This method is used to clear the OTP cached already
    public void clearOTP(String email)
    {
        otpCache.invalidate(email);
    }

}
