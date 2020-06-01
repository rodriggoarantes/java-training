import com.training.application.ApiService;
import com.training.application.impl.SwapiServiceImpl;
import com.training.domain.Planet;

public class Main {
    public static void main(String[] args) {
        final ApiService service = new SwapiServiceImpl();

        final Planet planet = service.planet();
        System.out.println(String.format("Planeta: %s", planet.getName()));
    }
}