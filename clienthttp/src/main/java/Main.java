import com.training.application.PostService;
import com.training.application.impl.PostServiceImpl;
import com.training.domain.Post;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        PostService service = new PostServiceImpl();

        List<Post> list = service.listPosts();

        list.forEach(i -> {System.out.println(i.getAuthor());});
    }
}