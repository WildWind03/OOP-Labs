#include "GameConf.h"

GameConf::GameConf(GamerType fPlayer, GamerType sPlayer, size_t countRound)
{
	this -> fPlayer = fPlayer;
	this -> sPlayer = sPlayer;
	this -> countRound = countRound;
}

GamerType GameConf::getFPlayer() const
{
	return fPlayer;
}

GamerType GameConf::getSPlayer() const
{
	return sPlayer;
}

size_t GameConf::getCountRound() const
{
	return countRound;
}

GameConf::~GameConf()
{

}
