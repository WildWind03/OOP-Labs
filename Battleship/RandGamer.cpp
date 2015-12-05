#include "RandGamer.h"

RandGamer::RandGamer() : Gamer()
{
	shotCounter = 0;
	shipCounter = 0;
	srand(time(NULL));
}

void RandGamer::fillShotList(size_t hField, size_t wField)
{
	shots.clear();

	for (size_t i = 0; i < hField; ++i)
	{
		for (size_t k = 0; k < wField; ++k)
		{
			ShotPoint p(i, k);

			shots.push_back(p);
		}
	}

	std::random_shuffle(shots.begin(), shots.end());
}

void RandGamer::fillShipList(size_t hField, size_t wField)
{
	ships.clear();

	for (size_t i = 0; i < hField; ++i)
	{
		for (size_t k = 0; k < wField; ++k)
		{
			bool isVert = myRand::getRand(0, 1);

			ShipPoint p(i, k, isVert);

			ships.push_back(p);
		}
	}

	std::random_shuffle(shots.begin(), shots.end());
}

void RandGamer::onGameStarted(size_t hField, size_t wField)
{
	shotCounter = 0;
	shipCounter = 0;

	fillShipList(hField, wField);
	fillShotList(hField, wField);

	isReady = true;
}

void RandGamer::onGameEnded(bool isWon)
{
	isReady = false;
}

void RandGamer::onRecieveShotState(const ShotState & state, const ShotPoint & p)
{
	++shotCounter;
}

void RandGamer::onRecieveResultOfPlacingShip(bool isPlaced)
{
	++shipCounter;
}

ShipPoint RandGamer::getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV)
{
	if (false == isReadyToStart())
	{
		throw std::runtime_error(initError);
	}

	return ships[shipCounter];
}

ShotPoint RandGamer::getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV)
{
	if (false == isReadyToStart())
	{
		throw std::runtime_error(initError);
	}

	return shots[shotCounter];
}

RandGamer::~RandGamer()
{
	ships.clear();
	shots.clear();
}
