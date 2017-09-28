#include <stdio.h> 
#include <stdlib.h>

#define teste 8

int main(){
	switch (teste) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			printf("ERRO"); 

			break;
		case 8:
			printf("OK"); 

			break;
		default:
			printf("ERRO"); 

	}
	return 0; 
}
