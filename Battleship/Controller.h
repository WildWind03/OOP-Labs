#pragma once 

#include "GameConf.h"
#include "Game.h"
#include "View.h"
#include "GamerType.h"
#include "MyFieldView.h"
#include "EnemyFieldView.h"
#include "Statistics.h"
#include "ConsoleView.h"


#include <cstdio>

class Controller
{
	Gamer * g1;
	Gamer * g2;

	Game * game;

	Statistics * stat;
	
	size_t countOfRounds;

public:

	Controller(GameConf & conf);

	void beginGame();

	~Controller();
};