package seal.VideoService.user;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserAdapter {
  public User getUserDetail(long userId) {
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://userservice-seal-top.mybluemix.net/" + userId;
    User user = restTemplate.getForObject(url, User.class);
    return user;
  }
}
