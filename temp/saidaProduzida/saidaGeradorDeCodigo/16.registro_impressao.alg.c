#include <stdio.h> 
#include <stdlib.h>

int main(){
		struct {	char nome[80];	int idade;	} reg;
	printf(reg.nome," tem ",reg.idade," anos");
	return 0; 
}
