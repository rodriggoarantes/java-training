import com.training.mvc.api.AlunoResource;

public class Main {
    public static void main(String[] args) {
        final AlunoResource api = new AlunoResource();
        final boolean success = api.print();

        if (success) {
            System.out.println("Finalizado com Sucesso!");
        }
    }
}
