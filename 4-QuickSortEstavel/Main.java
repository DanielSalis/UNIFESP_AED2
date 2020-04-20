import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public class Pessoa {
        String nome;
        int idade;
        int posicaoInicial;
        int posicaoFinal;
    }

    List<Pessoa> Pessoas = new ArrayList<Pessoa>();
    int posicaoNomeImpresso;
    int quantidadeNomesImpressos;

    public void setData(Scanner scanner) {
        int numeroPessoas = scanner.nextInt();

        for (int i = 0; i < numeroPessoas; i++) {
            Pessoa pessoa = new Pessoa();
            pessoa.nome = scanner.next();
            pessoa.idade = scanner.nextInt();
            pessoa.posicaoInicial = i;
            pessoa.posicaoFinal = i;

            Pessoas.add(pessoa);
        }

        posicaoNomeImpresso = scanner.nextInt();
        quantidadeNomesImpressos = scanner.nextInt();
    }

    public int encontrarPivo() {
        int p = posicaoNomeImpresso - 1;
        int r = Pessoas.size() - 1;
        int meio = (p + r) / 2;

        Pessoa a = Pessoas.get(p);
        Pessoa b = Pessoas.get(meio);
        Pessoa c = Pessoas.get(r);

        int mediana = 0;

        if (a.idade < b.idade) {
            if (b.idade < c.idade) {
                mediana = meio;
            }

            else {
                if (a.idade < c.idade)
                    mediana = r;
                else
                    mediana = p;
            }
        }

        else {
            if (c.idade < b.idade)
                mediana = meio;

            else {
                if (c.idade < a.idade)
                    mediana = r;

                else
                    mediana = p;
            }
        }

        return mediana;
    }

    public void quickSort(List<Pessoa> pessoas, int p, int r) {
        if (p < r) {
            int j = separar(pessoas, p, r);
            quickSort(pessoas, p, r - 1);
            quickSort(pessoas, j + 1, r);
        }
    }

    public int separar(List<Pessoa> pessoas, int p, int r) {
        int i = p + 1;
        int j = r;
        Pessoa pivo = Pessoas.get(p);
        while (i <= j) {
            Pessoa pi = Pessoas.get(i);
            Pessoa pj = Pessoas.get(j);
            if (pi.idade <= pivo.idade)
                i++;
            else if (pj.idade > pivo.idade)
                j--;
            else if (i <= j) {
                trocar(pessoas, i, j);
                i++;
                j--;
            }
        }

        trocar(pessoas, p, j);
        return j;
    }

    public void trocar(List<Pessoa> pessoas, int i, int j) {
        Pessoa pi = Pessoas.get(i);
        Pessoa pj = Pessoas.get(j);

        Pessoa aux = pi;
        pessoas.set(i, pj);
        pessoas.set(j, aux);
    }

    public void imprimirPessoas() {
        for (Pessoa p : Pessoas) {
            System.out.println(p.nome + " " + p.idade);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);

        main.setData(scanner);

        int medianaIndice = main.encontrarPivo();

        main.quickSort(main.Pessoas, 3, main.Pessoas.size() - 1);

        main.imprimirPessoas();

        scanner.close();
    }
}