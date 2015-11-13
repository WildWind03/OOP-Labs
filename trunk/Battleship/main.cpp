#pragma once

//#include "optionparser.h"
#include "Controller.h"

int main (int argc, char *argv[])
{
	GameConf conf("ConsoleGamer", "RandGamer", 1);

	Controller constroller(&conf);

	myGame.init();
	myGame.begin();
	
	return 0;
}