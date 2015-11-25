#pragma once

#include "Gamer.h"
#include "Field.h"
#include "FieldPoint.h"
#include "SimplePoint.h"

class OptGamer : public Gamer
{

public:
	
	OptGamer(MyFieldView * myFieldV, EnemyFieldView * enemyFieldV) : Gamer (myFieldV, enemyFieldV) {}

	FieldPoint getPoint(const size_t sizeOfShip) {}
	
	virtual SimplePoint getPointForShot () {}
};