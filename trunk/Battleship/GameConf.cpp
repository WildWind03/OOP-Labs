#include "GameConf.h"

GameConf::GameConf(GamerType fPlayer, GamerType sPlayer, size_t countRound)
{
	this -> fPlayer = fPlayer;
	this -> sPlayer = sPlayer;
	this -> countRound = countRound;
}

GamerType GameConf::getFPlayer()
{
	return fPlayer;
}

GamerType GameConf::getSPlayer()
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
