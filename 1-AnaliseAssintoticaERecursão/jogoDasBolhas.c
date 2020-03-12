#include <stdlib.h>
#include <stdio.h>

void recebeEntradas(int vetor[], int tamanhoDoVetor){
    for(int i=0; i<tamanhoDoVetor; i++){
        scanf("%d", &vetor[i]);
    }
    printf("\n");
}

void imprimeEntradas(int vetor[], int tamanhoDoVetor){
    for(int i=0; i<tamanhoDoVetor; i++){
        printf("%d ", vetor[i]);
    }
    printf("\n");
}

int bubbleSort(int vetor[], int tamanhoDoVetor){
    int count = 1;
    for(int i = tamanhoDoVetor -1; i > 0; i--){
        for(int j = 0; j < i; j++){
            if(vetor[j] > vetor[j+1]){
                int temp = vetor[j];
                vetor[j] = vetor[j+1];
                vetor[j+1] = temp;
                count = count + 1;
            }
        }
    }

    return count;
}

void exibirVencedor(int resultado){
    printf("%d \n", resultado);
    if(resultado%2 == 0){
        printf("Fulano");
    }else{
        printf("Ciclano");
    }
}

int main(){
    int numeroDeEntradas = 0;
    printf("Numero de entradas: ");
    scanf("%d", &numeroDeEntradas);

    int vetorSequencia[numeroDeEntradas];
    recebeEntradas(vetorSequencia, numeroDeEntradas);
    
    int resultado = bubbleSort(vetorSequencia, numeroDeEntradas);
    imprimeEntradas(vetorSequencia, numeroDeEntradas);

    exibirVencedor(resultado);

    return 0;
}