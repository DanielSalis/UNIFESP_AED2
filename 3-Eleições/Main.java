import java.util.Scanner;

public class Main{
    int eleitores;
    int votos;
    int []votosPrimeiroTurno;
    int votosContabilizadosDoPrimeiroTurno = 0;
    int votosContabilizadosDoSegundoTurno = 0;
    int []votesVector;
    int [][]votesMatrix; 

    public Main(int lines, int columns){
        this.eleitores = lines;
        this.votos = columns;

        votesVector = new int[columns];
        votosPrimeiroTurno = new int[columns];

        for(int i=0; i<columns; i++){
            votesVector[i] = 0;
            votosPrimeiroTurno [i] = 0;
        }

        votesMatrix = new int[lines][columns];
    }

    public void setVotesMatrixValues(Scanner scanner){
        for(int i=0; i<eleitores; i++){
            for(int j=0; j<votos; j++){
                votesMatrix[i][j] = scanner.nextInt();
            }
            countVotes(i);
        }
        setVotosDoPrimeiroTurno();
    }

    public void setVotosDoPrimeiroTurno(){
        for(int i=0; i<eleitores; i++){
            for(int j=0;j<votos;j++){
                if(votesMatrix[i][0] == j+1){
                    votosContabilizadosDoPrimeiroTurno++;
                    votosPrimeiroTurno[j]++;
                }
            }
        }
    }

    public float getResultadoDoPrimeiroTurno(){
        float maior = -1;
        float [] porcentagem = new float[votesVector.length]; 
        for(int i=0; i<votesVector.length; i++){
            porcentagem[i] = ((float) votosPrimeiroTurno[i] / (float) votosContabilizadosDoPrimeiroTurno);
            if(maior < porcentagem[i]){
                maior = porcentagem[i];
            }
            // System.out.println(i+1 + ": " + porcentagem[i]);
        }

        return maior;
    }

    public void printVotosPrimeiroTurno(){
        for(int j=0; j<votos; j++){
                System.out.println(j+1 + ": " + votosPrimeiroTurno[j]);
            }
    }

    public void countVotes(int line){
        for(int j=0; j<votos; j++){
            if(votesMatrix[line][j] == j+1){
                votosContabilizadosDoSegundoTurno++;
                votesVector[j]++;
            }
        }
    }

    public void getResultadoDoSegundoTurno(){
        float [] porcentagem = new float[votosPrimeiroTurno.length]; 
        for(int i=0; i<votosPrimeiroTurno.length; i++){
            porcentagem[i] = ((float) votesVector[i] / (float) votosContabilizadosDoSegundoTurno);
            System.out.println(i+1 + ": " + porcentagem[i]);
        }
        //Fazer a ordenação das porcentagens
    }

    public void printVotes(){
        for(int j=0; j<votos; j++){
                System.out.println(j+1 + ": " + votesVector[j]);
            }
    }

    public void printVotesMatrix(){
        for(int i=0; i<eleitores; i++){
            for(int j=0; j<votos; j++){
                System.out.print(votesMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        int lines = scanner.nextInt();
        int columns = scanner.nextInt();
        
        Main main = new Main(lines, columns);

        main.setVotesMatrixValues(scanner);
        System.out.println();
        float maiorResultadoPrimeiroTurno = main.getResultadoDoPrimeiroTurno();
        
        if(maiorResultadoPrimeiroTurno >= 0.5){
            System.out.println(maiorResultadoPrimeiroTurno);
        }else{
            main.getResultadoDoSegundoTurno();
        }
        scanner.close();
    }
}