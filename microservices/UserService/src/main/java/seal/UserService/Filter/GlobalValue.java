package seal.UserService.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalValue {
    
    public static String secretKey;
    public static long EXPIRATION_TIME = 1000 * 60000;

    @Value("${authenservice.jwt.secret}")
    public void setSecretKey(String secretKey) {
        GlobalValue.secretKey = secretKey;
    }
}
