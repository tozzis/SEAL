package seal.VideoService.view;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewService {
    
    @Autowired
    private ViewRepository viewRepository;
    
    public List<View> getCommentByVideoId(String videoId) {
        return viewRepository.findByVideoId(videoId);
    }
    
    public View saveView(View view){
        return viewRepository.save(view);
    }
}
