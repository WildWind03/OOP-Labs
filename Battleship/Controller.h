#pragma once 

#include "GameConf.h"
#include "Game.h"
#include "View.h"
#include "GamerType.h"
#include "MyFieldView.h"
#include "EnemyFieldView.h"
#include "Statistics.h"
#include "ConsoleView.h"
#include "GameExitException.h"


#include <cstdio>

class Controller
{
	Gamer * gamer1;
	Gamer * gamer2;

	Game * game;

	Statistics * stat;
	
	size_t countOfRounds;

public:

	Controller(GameConf & conf);

	void beginGame();

	virtual ~Controller();
};