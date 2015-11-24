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
	Field *fField;
	Field *sField;

	SimpleField *fShots;
	SimpleField *sShots;

	Gamer *fGamer;
	Gamer *sGamer;

	Game *game;

	View *view;

	size_t currentRound;
	size_t countOfRounds;

public:

	Controller(GameConf & conf);

	void beginGame();

	~Controller();
};