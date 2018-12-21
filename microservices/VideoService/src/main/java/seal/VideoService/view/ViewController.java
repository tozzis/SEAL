package seal.VideoService.view;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import seal.VideoService.Filter.TokenAuthenticationService;
import seal.VideoService.video.Video;
import seal.VideoService.video.VideoAdapter;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ViewController {
    
    @Autowired
    private ViewService viewService;
    
    @Autowired
    private VideoAdapter videoAdapter;
    
    @GetMapping("/views/video/{videoId}")
    public ResponseEntity <Integer> getCountVideo(@PathVariable String videoId,HttpServletRequest request){
        TokenAuthenticationService.validateJWTAuthentication(request);
        Video video = videoAdapter.findVideoById(videoId);
        if(video!=null){
            List<View> view = viewService.getCommentByVideoId(videoId);
            return new ResponseEntity<>(view.size(), HttpStatus.OK);
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ไม่เจอ video ตัวนี้");
        }
    }
    
    @PostMapping("/views/video")
    public ResponseEntity <View> countVideo(@RequestBody View view,HttpServletRequest request){
        TokenAuthenticationService.validateJWTAuthentication(request);
        Video video = videoAdapter.findVideoById(view.getVideoId());
        if(video!=null){
            viewService.saveView(view);
            return new ResponseEntity<>(view, HttpStatus.OK);
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ไม่เจอ video ตัวนี้");
        }
    }
}