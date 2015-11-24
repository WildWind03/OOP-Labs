#include "RandGamer.h"

RandGamer::RandGamer(MyFieldView * myFieldV, EnemyFieldView * enemyFieldV) : Gamer(myFieldV, enemyFieldV) 
{
	
}

FieldPoint RandGamer::getPoint(const size_t sizeOfShip)
{
	size_t h = myRand::getRand(0, myFieldV -> getHeight() - 1);
	size_t w = myRand::getRand(0, myFieldV -> getWidth() - 1);

	bool isVert = myRand::getRand(0,1);

	FieldPoint p(h, w, isVert);

	return p;
}

RandGamer::~RandGamer()
{

}
