package zw.co.codehive.transactiondesignpatterns.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "akupay-api-gateway", url = "${hsm.url}")
public interface HSMFeignClient {

    @GetMapping("/genKey")
    String generateKey(@RequestParam("alg") String alg,
                       @RequestParam("keyType") String keyType);

    @GetMapping("/genKeyCmp")
    String generateKeyFromComponents(@RequestParam("cmps") String cmps,
                                     @RequestParam("keyType") String keyType,
                                     @RequestParam("alg") String alg);

    @GetMapping("/checkKey")
    String calculateKeyCheckValue(@RequestParam("alg") String alg,
                                  @RequestParam("keyType") String keyType,
                                  @RequestParam("key") String key);


    @GetMapping("/crypt")
    String crypt(@RequestParam("data") String data,
                 @RequestParam("dataType") String dataType,
                 @RequestParam("key") String key,
                 @RequestParam("keyType") String keyType,
                 @RequestParam("function") String function,
                 @RequestParam("alg") String alg,
                 @RequestParam("outputType") String outputType
    );

    @GetMapping("/trans")
    String translateKey(@RequestParam("data") String data,
                        @RequestParam("key") String key,
                        @RequestParam("keyType") String keyType,
                        @RequestParam("key2") String key2,
                        @RequestParam("keyType2") String keyType2
    );

    @GetMapping("/genMac")
    String generateMac(@RequestParam("data") String data,
                       @RequestParam("dataType") String dataType,
                       @RequestParam("key") String key,
                       @RequestParam("keyType") String keyType,
                       @RequestParam("outputType") String outputType
    );

    @GetMapping("/pinblock")
    String generatePinblock(@RequestParam("pin") String pin,
                            @RequestParam("pan") String pan,
                            @RequestParam("key") String key,
                            @RequestParam("keyType") String keyType,
                            @RequestParam("alg") String alg
    );

    @GetMapping("/cvv")
    String calculateCVV(@RequestParam("pan") String pan,
                        @RequestParam("cvkpair") String cvkpair,
                        @RequestParam("svc") String svc,
                        @RequestParam("expdate") String expdate
    );


    @GetMapping("/pvv")
    String calculatePVV(@RequestParam("pan") String pan,
                        @RequestParam("pvkpair") String pvkpair,
                        @RequestParam("PVKI") String PVKI,
                        @RequestParam("pin") String pin
    );


}
