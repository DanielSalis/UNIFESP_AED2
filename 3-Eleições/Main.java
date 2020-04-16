import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public class Candidato {
        int id;
        int votosRecebidosPrimeiroTurno;
        int votosRecebidosSegundoTurno;
        float pctDeVotosPrimeiroTurno;
        float pctDeVotosSegundoTurno;
        int totalVotosInvalidosSegundoTurno = 0;
        boolean temAlgumVoto = false;
    }

    List<Candidato> Candidatos = new ArrayList<Candidato>();
    int numEleitores;
    int numCandidatos;
    int numVotos = 3;
    int[][] votesMatrix;

    public Main(int lines, int columns) {
        this.numEleitores = lines;
        this.numCandidatos = columns;

        for (int i = 0; i < numCandidatos; i++) {
            Candidato novoCandidato = new Candidato();
            novoCandidato.id = i + 1;
            novoCandidato.votosRecebidosPrimeiroTurno = 0;
            Candidatos.add(novoCandidato);
        }

        votesMatrix = new int[lines][columns];
    }

    public void printCandidatos() {
        for (Candidato obj : Candidatos) {
            System.out.println(obj.id + ": " + obj.votosRecebidosPrimeiroTurno);
        }
        System.out.println();
    }

    public void setVotesMatrixValues(Scanner scanner) {
        for (int i = 0; i < numEleitores; i++) {
            for (int j = 0; j < numVotos; j++) {
                votesMatrix[i][j] = scanner.nextInt();
            }
        }
    }

    public void printVotesMatrix() {
        for (int i = 0; i < numEleitores; i++) {
            for (int j = 0; j < numVotos; j++) {
                System.out.print(votesMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void getResultadosPrimeiroTurno() {
        int totalDeVotosValidos = 0;
        for (int i = 0; i < numEleitores; i++) {
            for (Candidato candidato : Candidatos) {
                if (candidato.id == votesMatrix[i][0]) {
                    totalDeVotosValidos++;
                    candidato.votosRecebidosPrimeiroTurno++;
                }
            }
        }

        boolean todosOsCandidatosSaoInvalidos = true;
        for (Candidato candidato : Candidatos) {
            candidato.pctDeVotosPrimeiroTurno = (float) candidato.votosRecebidosPrimeiroTurno
                    / (float) totalDeVotosValidos;
            if (candidato.pctDeVotosPrimeiroTurno >= 0.5) {
                todosOsCandidatosSaoInvalidos = false;
                System.out.print(candidato.id + " ");
                System.out.printf("%.2f", candidato.pctDeVotosPrimeiroTurno * 100);
                return;
            }
            if (candidato.pctDeVotosPrimeiroTurno > 0) {
                todosOsCandidatosSaoInvalidos = false;
            }
        }

        if (todosOsCandidatosSaoInvalidos) {
            System.out.print(0);
            return;
        }

        List<Candidato> candOrdenados = selectionSort(Candidatos);

        Candidato cand1_ = candOrdenados.get(candOrdenados.size() - 1);
        Candidato cand2_ = candOrdenados.get(candOrdenados.size() - 2);

        if(cand1_.votosRecebidosPrimeiroTurno == cand2_.votosRecebidosPrimeiroTurno){
            System.out.print(cand2_.id + " ");
            System.out.printf("%.2f", cand2_.pctDeVotosPrimeiroTurno * 100);
            System.out.println();
    
            getResultadosSegundoTurno(cand1_, cand2_);

            return;
        }
        else{
            Candidato c = candOrdenados.get(candOrdenados.size()-1);
            System.out.print(c.id + " ");
            System.out.printf("%.2f", c.pctDeVotosPrimeiroTurno * 100);
            System.out.println();
    
            getResultadosSegundoTurno(candOrdenados.get(candOrdenados.size() - 1),
            candOrdenados.get(candOrdenados.size() - 2));
        }

    }

    public void getResultadosSegundoTurno(Candidato candidato_1, Candidato candidato_2) {
        int totalDeVotosValidos = numEleitores;

        
        for (int i = 0; i < numEleitores; i++) {
            boolean nenhumVoto = true;
            for (int j = 0; j < numVotos; j++) {
                if (candidato_2.id == votesMatrix[i][j]) {
                    candidato_2.votosRecebidosSegundoTurno++;
                    nenhumVoto = false;
                    break;
                } else if (candidato_1.id == votesMatrix[i][j]) {
                    candidato_1.votosRecebidosSegundoTurno++;
                    nenhumVoto = false;
                    break;
                }
            }
            if (nenhumVoto == true) {
                totalDeVotosValidos--;
            }
        }

        List<Candidato> candidatosMaisVotados = new ArrayList<Candidato>();
        candidatosMaisVotados.add(candidato_1);
        candidatosMaisVotados.add(candidato_2);

        for (Candidato candidato : candidatosMaisVotados) {
            candidato.pctDeVotosSegundoTurno = (float) candidato.votosRecebidosSegundoTurno
                    / (float) (totalDeVotosValidos);
        }

        candidatosMaisVotados = selectionSortII(candidatosMaisVotados);

        if(candidato_1.pctDeVotosSegundoTurno == candidato_2.pctDeVotosSegundoTurno){
            Candidato cand = new Candidato();
            if(candidato_1.id < candidato_2.id){
                cand = candidato_1;
            }else{
                cand = candidato_2;
            }
            System.out.print(cand.id + " ");
            System.out.printf("%.2f", cand.pctDeVotosSegundoTurno * 100);
            System.out.println();

            return;
        }

        int size = candidatosMaisVotados.size() - 1;

        Candidato c = candidatosMaisVotados.get(size);
        System.out.print(c.id + " ");
        System.out.printf("%.2f", c.pctDeVotosSegundoTurno * 100);
        System.out.println();
        
    }

    public List<Candidato> selectionSort(List<Candidato> candidatos) {
        for (int i = 0; i < candidatos.size(); i++) {
            int menor = i;
            for (int j = i + 1; j < candidatos.size(); j++) {
                Candidato candAtual = candidatos.get(j);
                Candidato candmenor = candidatos.get(menor);
                if (candAtual.votosRecebidosPrimeiroTurno < candmenor.votosRecebidosPrimeiroTurno) {
                    menor = j;
                }
            }

            Candidato aux = candidatos.get(menor);
            candidatos.set(menor, candidatos.get(i));
            candidatos.set(i, aux);
        }
        return candidatos;
        // System.out.println(Candidatos);
    }

    public List<Candidato> selectionSortII(List<Candidato> candidatos) {
        for (int i = 0; i < candidatos.size(); i++) {
            int menor = i;
            for (int j = i + 1; j < candidatos.size(); j++) {
                Candidato candAtual = candidatos.get(j);
                Candidato candmenor = candidatos.get(menor);
                if (candAtual.pctDeVotosSegundoTurno < candmenor.pctDeVotosSegundoTurno) {
                    menor = j;
                }
            }

            Candidato aux = candidatos.get(menor);
            candidatos.set(menor, candidatos.get(i));
            candidatos.set(i, aux);
        }
        return candidatos;
        // System.out.println(Candidatos);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int lines = scanner.nextInt();
        int columns = scanner.nextInt();

        Main main = new Main(lines, columns);

        main.setVotesMatrixValues(scanner);
        main.getResultadosPrimeiroTurno();

        scanner.close();
    }
}
