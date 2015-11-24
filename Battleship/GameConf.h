#pragma once

#include "GamerType.h"

#include <cstdio>
#include <iostream>
#include <string>

class GameConf
{
	GamerType fPlayer;
	GamerType sPlayer;

	size_t countRound;

public:

	GameConf(GamerType fPlayer, GamerType sPlayer, size_t countRound);

	GamerType getFPlayer();
	GamerType getSPlayer();

	size_t getCountRound();

	~GameConf();

};
