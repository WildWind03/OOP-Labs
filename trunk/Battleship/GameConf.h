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

	GameConf (const GameConf & conf) = delete;
	GameConf & operator=(const GameConf & conf) = delete;
	GameConf() = delete;

	GamerType getFPlayer() const;
	GamerType getSPlayer() const;

	size_t getCountRound() const;

	virtual ~GameConf();

};
