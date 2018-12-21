package seal.VideoService.view;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Long>{
    List<View> findByVideoId(String videoId);
}
