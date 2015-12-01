#include "OptGamer.h"

OptGamer::OptGamer() : Gamer () 
{

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

}

OptGamer::~OptGamer(){}