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
    }

    List<Candidato> Candidatos = new ArrayList<Candidato>();
    int numEleitores;
    int numVotos;
    int[][] votesMatrix;

    public Main(int lines, int columns) {
        this.numEleitores = lines;
        this.numVotos = columns;

        for (int i = 0; i < columns; i++) {
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

        for (Candidato candidato : Candidatos) {
            candidato.pctDeVotosPrimeiroTurno = (float) candidato.votosRecebidos / (float) totalDeVotosValidos;
            if (candidato.pctDeVotosPrimeiroTurno >= 0.5) {
                System.out.println(candidato.id + ": " + candidato.pctDeVotosPrimeiroTurno);
                return;
            }
        }

        // OrdenarVetor de Candidatos
        // pegar os dois com a maior porcentagem do primeiro turno

        getResultadosSegundoTurno(candidato_1, candidato_2);
    }

    public void getResultadosSegundoTurno(Candidato candidato_1, Candidato candidato_2) {
        int totalDeVotosValidos = 0;
        for (int i = 0; i < numEleitores; i++) {
            for (int j = 0; j < numVotos; j++) {
                if (candidato_1.id == votesMatrix[i][j]) {
                    totalDeVotosValidos++;
                    candidato_1.votosRecebidosSegundoTurno++;
                    break;
                } else if (candidato_2.id == votesMatrix[i][j]) {
                    totalDeVotosValidos++;
                    candidato_2.votosRecebidosSegundoTurno++;
                    break;
                }
            }
        }
    }

    public void selectionSort() {

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