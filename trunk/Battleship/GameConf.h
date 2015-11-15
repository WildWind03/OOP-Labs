#pragma once

#include <cstdio>
#include <iostream>
#include <string>

class GameConf
{
	std::string fPlayer;
	std::string sPlayer;

	size_t countRound;

public:

	GameConf(std::string fPlayer, std::string sPlayer, size_t countRound);

	std::string getFPlayer();
	std::string getSPlayer();

	size_t getCountRound();

	~GameConf();

};
