#pragma once
#include "optionparser.h"
#include "GameConf.h"

int main (int argc, char *argv[])
{
	GameConf conf();

	Game myGame(&conf);

	myGame.init();
	myGame.begin();
	
	return 0;
}