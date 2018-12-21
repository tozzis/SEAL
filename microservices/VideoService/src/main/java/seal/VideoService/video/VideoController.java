package seal.VideoService.video;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seal.VideoService.Filter.TokenAuthenticationService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class VideoController {

    @Autowired
    private VideoAdapter videoAdapter;
    
    @GetMapping("/video/{id}")
     public ResponseEntity<Video> findVideoById(@PathVariable String id, HttpServletRequest request) {
        System.out.println("26");
        TokenAuthenticationService.validateJWTAuthentication(request);
        Video video = videoAdapter.findVideoById(id);
        return new ResponseEntity<Video>(video, HttpStatus.OK);
    }

    @GetMapping("/videos")
    //@RequestMapping(path = "/videos", method = RequestMethod.GET)
    public ResponseEntity<List<Video>> findAllVideo(HttpServletRequest request) {
        TokenAuthenticationService.validateJWTAuthentication(request);
        System.out.println("dsfsd");
        List<Video> videosResponse = videoAdapter.findAllVideo();
        return new ResponseEntity<List<Video>>(videosResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/subject/{id}/videos", method = RequestMethod.GET)
    public ResponseEntity<List<Video>> findVideoFromSubjectId(@PathVariable String id, HttpServletRequest request) {
        TokenAuthenticationService.validateJWTAuthentication(request);
        List<Video> videosFromSubjectResponse = videoAdapter.findVideoFromSubjectId(id);
        return new ResponseEntity<List<Video>>(videosFromSubjectResponse, HttpStatus.OK);
    }

}
