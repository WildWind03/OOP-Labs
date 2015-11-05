#pragma once 
#include "GameConf.h"
#include "GamerFactory.h"

class Game
{

	GameConf *conf;

	Field *fField.
	Field *sField;

	Gamer *fGamer;
	Gamer *sGamer;

	size_t currentRound;

public:

	Game(GameConf *conf);

	void init();

	void begin();

	~Game();
};