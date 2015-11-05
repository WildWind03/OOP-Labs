#include "GameConf.h"

GameConf::GameConf(std::string fPlayer, std::string sPlayer, size_t countRound);
{
	fPlayer = fPlayer;
	sPlayer = sPlayer;
	countRound = countRound;
}

std::string GameConf::getFPlayer()
{
	return fPlayer;
}

std::string GameConf::getSPlayer()
{
	return sPlayer;
}

size_t GameConf::geCountOfRounds()
{
	return countRound;
}

GameConf::~GameConf() {}