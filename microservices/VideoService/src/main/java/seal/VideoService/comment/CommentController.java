package seal.VideoService.comment;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import seal.VideoService.Filter.TokenAuthenticationService;
import seal.VideoService.user.User;
import seal.VideoService.user.UserAdapter;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private UserAdapter userAdapter;
    
    @RequestMapping(path = "/comments",method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getAllCommentInSystem(HttpServletRequest request) {
        TokenAuthenticationService.validateJWTAuthentication(request);
        List<Comment> comment = commentService.getAllComment();
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    
    @RequestMapping(path = "/comments/video/{videoId}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getAllCommentInThatVideo(@PathVariable String videoId,HttpServletRequest request) {
        TokenAuthenticationService.validateJWTAuthentication(request);
        List<Comment> comment = commentService.getCommentByVideoId(videoId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    
    @RequestMapping(path = "/comments/video/{videoId}", method = RequestMethod.POST)
    public ResponseEntity<Comment> saveCommentFromUserToSystem(@PathVariable String videoId, @RequestBody CommentRequest commentData,HttpServletRequest request) {
        TokenAuthenticationService.validateJWTAuthentication(request);
        User user = userAdapter.getUserDetail(commentData.getUserId());
        Comment comment = new Comment(user, videoId, commentData.getComment());
        commentService.saveCommentFromController(comment);
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }
}
