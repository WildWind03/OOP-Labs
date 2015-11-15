#include "GameConf.h"

GameConf::GameConf(std::string fPlayer, std::string sPlayer, size_t countRound)
{
	this -> fPlayer = fPlayer;
	this -> sPlayer = sPlayer;
	this -> countRound = countRound;
}

std::string GameConf::getFPlayer()
{
	return fPlayer;
}

std::string GameConf::getSPlayer()
{
	return sPlayer;
}

size_t GameConf::getCountRound()
{
	return countRound;
}

GameConf::~GameConf()
{

}
