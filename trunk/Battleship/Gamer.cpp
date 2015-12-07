#include "Gamer.h"

Gamer::Gamer()
{
	isReady = false;
}

void Gamer::onRecieveShotState(const ShotState & state, const ShotPoint & prevShot)
{

}

void Gamer::onGetStatistics(const Statistics & stat)
{

}

void Gamer::onRecieveResultOfPlacingShip(bool isPlaced)
{

}

void Gamer::onRecieveErrorString(const std::string & errorStr)
{

}

void Gamer::onGameEnded(bool isWon)
{
	isReady = false;
}

void Gamer::onGameStarted(size_t hField, size_t wField)
{
	isReady = true;
}

bool Gamer::isReadyToStart() const
{
	return isReady;
}

Gamer::~Gamer()
{

}
