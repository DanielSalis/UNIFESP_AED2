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
    boolean caotic;

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

    public int encontrarPivo(int p, int r) {
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
            int q = separar(pessoas, p, r);
            quickSort(pessoas, p, q - 1);
            quickSort(pessoas, q + 1, r);
        }
    }

    public int separar(List<Pessoa> pessoas, int p, int r) {
        int mediana = encontrarPivo(p, r);
        int i = p + 1;
        int j = r;
        Pessoa pivo = pessoas.get(mediana);
        while (i <= j) {
            Pessoa pi = pessoas.get(i);
            Pessoa pj = pessoas.get(j);
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
        Pessoa pi = pessoas.get(i);
        Pessoa pj = pessoas.get(j);

        Pessoa aux = pi;
        pessoas.set(i, pj);
        pessoas.set(j, aux);
    }

    public void percorrerSemelhantes() {
        int size = Pessoas.size() - 1;
        int i;
        for (i = 0; i < size; i++) {
            Pessoa p1 = Pessoas.get(i);
            Pessoa p2 = Pessoas.get(i + 1);
            if (p1.idade == p2.idade) {
                if (p2.posicaoInicial == i) {
                    caotic = true;
                    return;
                }
            }
            caotic = false;
        }
    }

    public void imprimirPessoas() {
        for (Pessoa p : Pessoas) {
            System.out.println(p.nome + " " + p.idade);
        }
    }

    public void imprimirSelecionados() {
        int i;
        for (i = posicaoNomeImpresso - 1; i < quantidadeNomesImpressos + posicaoNomeImpresso - 1; i++) {
            Pessoa pi = Pessoas.get(i);
            System.out.println(pi.nome + " " + pi.idade);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);

        main.setData(scanner);

        main.quickSort(main.Pessoas, 0, main.Pessoas.size() - 1);

        main.percorrerSemelhantes();
        if (main.caotic) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
        // main.imprimirSelecionados();
        main.imprimirPessoas();
        System.out.println();

        scanner.close();
    }
}