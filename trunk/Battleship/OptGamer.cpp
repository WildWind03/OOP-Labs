#include "OptGamer.h"

OptGamer::OptGamer() : Gamer () 
{
	st = GamerState::EXPLORE;
	shotCounter = 0;
}

void OptGamer::fillShotList(size_t hField, size_t wField)
{
	stShots.clear();

	for (size_t i = 0; i < hField; ++i)
	{
		for (size_t k = 0; k < wField; ++k)
		{
			ShotPoint p(i, k);

			stShots.push_back(p);
		}
	}

	std::random_shuffle(stShots.begin(), stShots.end());
}
/*void OptGamer::init()
{
	/*standartShots.clear();

	for (size_t i = 3; i <= 15; i = i + 4)
	{
		for (size_t k = 0; k <= 3; ++k)
		{
			ShotPoint p(k, i - k);
			standartShots.push_back(p);
		}
	}
}

bool OptGamer::isInitialized() const
{
	return initialized;
}
*/

ShotPoint OptGamer::getNextStandartShot() const
{
	return stShots[shotCounter];
}

void OptGamer::onGameEnded(bool isWon)
{
	isReady = false;
}

void OptGamer::onGameStarted(size_t h, size_t w)
{
	isReady = true;
	shotCounter = 0;
	st = GamerState::EXPLORE;
	fillShotList(h, w);
}

void OptGamer::onRecieveShotState(ShotState state)
{
	++shotCounter;
}
 
ShipPoint OptGamer::getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) const 
{
	size_t h, w;
	bool isVert;

	if (1 == sizeOfShip)
	{
		h = myRand::getRand(2, myFieldV.getHeight() - 3);
		w = myRand::getRand(2, myFieldV.getWidth() - 3);

		isVert = true;
	}
	else
	{
		h = myRand::getRand(0, myFieldV.getHeight() - 1);

		if ((0 == h) || (myFieldV.getHeight() - 1 == h))
		{
			w = myRand::getRand(0, myFieldV.getWidth() - 1);
		}
		else
		{
			bool c = myRand::getRand(0,1);

			if (false == c)
			{
				w = 0;
			}
			else
			{
				w = myFieldV.getWidth() - 1;
			}
		}

		if (((0 == h) && (0 == w)) || ((0 == h) && ( myFieldV.getWidth() - 1 == w)) || (( myFieldV.getHeight() - 1 == h) && (0 == w)) || (( myFieldV.getHeight() - 1 == h) && ( myFieldV.getWidth() - 1 == w)))
		{
			isVert = myRand::getRand(0,1);
		}
		else
		{
			if ((0 == h) || ( myFieldV.getHeight() - 1 == h))
			{
				isVert = false;
			}
			else
			{
				isVert = true;
			}
		}
	}

	return ShipPoint(h, w, isVert);
}

ShotPoint OptGamer::getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const 
{
	if (GamerState::EXPLORE == st) 
	{
		ShotPoint p = getNextStandartShot();

		//enemyFieldV.isPossibleToBeShipThere(p);

		return p;
	}
	else
	{
		return ShotPoint(0, 0);
	}
}

OptGamer::~OptGamer()
{

}