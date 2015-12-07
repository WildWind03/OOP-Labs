#include "myRand.h"

size_t myRand::getRand(size_t start, size_t end)
{
	size_t dif = end - start + 1;
	
	size_t k = rand() % dif;

	return start + k;
}