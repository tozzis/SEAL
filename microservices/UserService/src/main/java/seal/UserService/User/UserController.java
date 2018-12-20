package seal.UserService.User;

import seal.UserService.Filter.TokenAuthenticationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seal.UserService.Exceptions.BadRequestException;
import seal.UserService.Exceptions.NotFoundException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    
    @Autowired
    private UserRepository userRepository;
    
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private String INCORRECT_USERID_INPUT_FORMAT = "USER ID ต้องเป็นตัวเลขรหัสนักศึกษาเท่านั้น";
    private String NOT_FOUND_USERID = "ไม่พบ USERID ในระบบ";
    private String NOT_FOUND_PASSWORD = "กรอก USERNAME หรือ PASSWORD ผิด";
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(HttpServletRequest request) {
        TokenAuthenticationService.validateJWTAuthentication(request);
        List<User> user = userService.getAllUsers();
        return new ResponseEntity<List<User>>(user, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUseById(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PostMapping(path = "/user/login")
    public ResponseEntity<HashMap> signInByStudentId(@RequestBody Map<String, String> user_input, HttpServletResponse response) {
        Long userId;
        String password;
        HashMap<String, Object> responseData = new HashMap();
        User user = null;
        try {
            userId = Long.parseLong(user_input.get("id").toString());
            password = user_input.get("password").toString();
        } catch (NumberFormatException numberFmtException) {
            throw new BadRequestException("User Input Incorrect Pattern !");
        }
        user = userService.getUserById(userId);
        
        if (user != null) {
            String userPassword = user.getPassword();
            if (userPassword.equals(password)) {
                String token = tokenAuthenticationService.createTokenUser(user);
                response.addHeader("Authorization", token);
                System.out.println(response.getHeaderNames());
                responseData.put("status", true);
                responseData.put("jwtToken", token);
                responseData.put("user", user);
                logger.info("User : " + user.getId() + " Authentication Success !");
                return new ResponseEntity<HashMap>(responseData, HttpStatus.OK);
            } else {
                throw new NotFoundException("Authentication User Fail !");
            }
        }
        throw new NotFoundException("Authentication User Fail !");
    }
}
