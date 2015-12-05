#pragma once 

#include <limits>
#include <ctime>
#include <random>
#include <cstdio>
#include <iostream>
#include <cstdlib>

class myRand
{
public:

	static size_t getRand(size_t start, size_t end)
	{
		size_t dif = end - start + 1;

		size_t k = rand() % dif;

		//std::cout << k << std::endl;
		//getchar();
		return start + k;
		//std::default_random_engine rng;	

		//rng.seed(std::random_device()());

	    //std::uniform_int_distribution<size_t> dist_a_b(start, end);

	    //return dist_a_b(rng);
	}
};