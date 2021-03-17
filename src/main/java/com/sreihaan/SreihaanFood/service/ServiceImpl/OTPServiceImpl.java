package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sreihaan.SreihaanFood.service.OTPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
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

    public Integer generateOTP(String email)
    {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        logger.info("OTP Generated, email -> "+email+" OTP : "+otp);
        otpCache.put(email, otp);
        return otp;
    }

    public Integer getOtp(String email)
    {
        try
        {
            return otpCache.get(email);
        }
        catch (Exception e)
        {
            logger.info("OTP not found, email -> "+email+" Exception : "+e.getMessage());
            return null;
        }
    }

    //This method is used to clear the OTP cached already
    public void clearOTP(String email)
    {
        otpCache.invalidate(email);
    }

}
