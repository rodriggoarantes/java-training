import com.totvs.domain.objetos.Aluno;
import com.totvs.domain.objetos.Professor;

import java.util.*;

public class Main {
    public static void main(String[] args) {

    }

    public void teste(List x) {

    }

    public void teste() {
        this.alterar(null);

        final Aluno teste = new Aluno(1);
        this.alterar(teste);
    }

    public void alterar(final Object aluno) {
        Queue<Aluno> fila = new LinkedList<>();
        final List<Aluno> alunos2 = new LinkedList<>();
        this.teste(new LinkedList<>());
    }
}
