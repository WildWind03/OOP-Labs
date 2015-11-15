#pragma once 

#include "GameConf.h"
#include "Game.h"
#include "View.h"
#include <cstdio>

class Controller
{
	Field *fField;
	Field *sField;

	Field *fFieldAttack;
	Field *sFieldAttack;

	Gamer *fGamer;
	Gamer *sGamer;

	Game *game;

	View *view;

	size_t currentRound;
	size_t countOfRounds;

public:

	Controller(GameConf *conf);

	void beginGame();

	~Controller();
};