#pragma once

#include "Field.h"
#include "Gamer.h"
#include "FieldPoint.h"
#include "SimplePoint.h"
#include "myRand.h"

#include <cstdio>

class RandGamer : public Gamer
{

public:

	RandGamer() = delete;

	RandGamer(MyFieldView * myFieldV, EnemyFieldView * enemyFieldV);

	virtual FieldPoint getPoint(const size_t sizeOfShip);

	virtual SimplePoint getPointForShot();

	~RandGamer();
};
