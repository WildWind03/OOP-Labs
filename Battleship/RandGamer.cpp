#include "RandGamer.h"

RandGamer::RandGamer()
{
	
}

ShipPoint RandGamer::getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) const
{
	size_t h = myRand::getRand(0, myFieldV.getHeight() - 1);
	size_t w = myRand::getRand(0, myFieldV.getWidth() - 1);

	bool isVert = myRand::getRand(0,1);

	ShipPoint p(h, w, isVert);

	return p;
}

ShotPoint RandGamer::getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const
{
	size_t h = myRand::getRand(0, myFieldV.getHeight() - 1);
	size_t w = myRand::getRand(0, myFieldV.getWidth() - 1);

	return ShotPoint(h, w);
}

RandGamer::~RandGamer()
{

}
