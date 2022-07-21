package zw.co.codehive.transactiondesignpatterns.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import zw.co.codehive.transactiondesignpatterns.feign.HSMFeignClient;

@Configuration
@Slf4j
public class KeyManagerConfiguration {


    private final HSMFeignClient hsmFeignClient;


    public KeyManagerConfiguration(HSMFeignClient hsmFeignClient) {
        this.hsmFeignClient = hsmFeignClient;
    }



    public String getZMKKey()  {
        String key = hsmFeignClient.generateKey("2DES", "CLEAR");
        ZMK zmk = new ZMK();
        zmk.setZmkKey(key);
        return  key;
    }

    private String encryptionKey(String encryptedKey) {
        return hsmFeignClient.crypt(
                encryptedKey,
                "HEXSTRING",
                "13AED5DA1F32347523C708C11F2608FD",
                "CLEAR",
                "DEC",
                "2TDES",
                "HEXSTRING"
        );
    }
}