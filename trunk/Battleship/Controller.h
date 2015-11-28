#pragma once 

#include "GameConf.h"
#include "Game.h"
#include "View.h"
#include "GamerType.h"
#include "MyFieldView.h"
#include "EnemyFieldView.h"


#include <cstdio>

class Controller
{
	Gamer *g1;
	Gamer *g2;

	Game *game;

	size_t currentRound;
	size_t countOfRounds;

public:

	Controller(GameConf & conf);

	void beginGame();

	~Controller();
};