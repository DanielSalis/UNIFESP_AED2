#include <stdlib.h>
#include <stdio.h>

int calcularExponencial(int numero, int expoente){
    if(expoente == 0){
        return 1;
    }else{
        return numero * calcularExponencial(numero, expoente - 1);
    }
}


int main(){

    int numero;
    printf("Digite o numero:\n");
    scanf("%d", &numero);

    int expoente;
    printf("Digite o expoente:\n");
    scanf("%d", &expoente);

    int resultado = calcularExponencial(numero, expoente);
    printf("%d", resultado);

    return 0;
}