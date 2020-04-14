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
                System.out.println(candidato.id + ": " + candidato.pctDeVotosPrimeiroTurno * 100);
                return;
            }
            if (candidato.pctDeVotosPrimeiroTurno > 0) {
                todosOsCandidatosSaoInvalidos = false;
            }
        }

        if (todosOsCandidatosSaoInvalidos) {
            System.out.println(0);
            return;
        }

        // OrdenarVetor de Candidatos
        List<Candidato> candidatosOrdenados = selectionSort(Candidatos);
        getResultadosSegundoTurno(candidatosOrdenados.get(candidatosOrdenados.size() - 1),
                candidatosOrdenados.get(candidatosOrdenados.size() - 2));

    }

    public void getResultadosSegundoTurno(Candidato candidato_1, Candidato candidato_2) {

        for (int i = 0; i < numEleitores; i++) {
            for (int j = 0; j < numVotos; j++) {
                candidato_2.temAlgumVoto = false;
                if (candidato_2.id == votesMatrix[i][j]) {
                    candidato_2.temAlgumVoto = true;
                    candidato_2.votosRecebidosSegundoTurno++;
                    break;
                } else if (candidato_1.id == votesMatrix[i][j]) {
                    candidato_1.temAlgumVoto = true;
                    candidato_1.votosRecebidosSegundoTurno++;
                    break;
                }
            }
        }

        int totalDeVotosValidos = numEleitores;
        for (int i = 0; i < numEleitores; i++) {
            for (int j = 0; j < numVotos; j++) {
                candidato_1.temAlgumVoto = false;
                if (candidato_1.id == votesMatrix[i][j]) {
                    candidato_1.temAlgumVoto = true;
                    break;
                }
            }
            if (candidato_1.temAlgumVoto == false) {
                candidato_1.totalVotosInvalidosSegundoTurno++;
            }
        }

        for (int i = 0; i < numEleitores; i++) {
            for (int j = 0; j < numVotos; j++) {
                candidato_2.temAlgumVoto = false;
                if (candidato_2.id == votesMatrix[i][j]) {
                    candidato_2.temAlgumVoto = true;
                    break;
                }
            }
            if (candidato_2.temAlgumVoto == false) {
                candidato_2.totalVotosInvalidosSegundoTurno++;
            }
        }

        List<Candidato> candidatosMaisVotados = new ArrayList<Candidato>();
        candidatosMaisVotados.add(candidato_1);
        candidatosMaisVotados.add(candidato_2);

        for (Candidato candidato : candidatosMaisVotados) {
            candidato.pctDeVotosSegundoTurno = (float) candidato.votosRecebidosSegundoTurno
                    / (float) (totalDeVotosValidos - candidato.totalVotosInvalidosSegundoTurno);
        }

        candidatosMaisVotados = selectionSort(candidatosMaisVotados);

        int size = candidatosMaisVotados.size() - 1;
        for (int i = size; i >= 0; i--) {
            Candidato c = candidatosMaisVotados.get(i);
            System.out.print(c.id + " ");
            System.out.printf("%.2f", c.pctDeVotosSegundoTurno * 100);
            System.out.println();
        }
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