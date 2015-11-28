#pragma once

#include "Gamer.h"
#include "Field.h"
#include "ShipPoint.h"
#include "ShotPoint.h"

class OptGamer : public Gamer
{

public:
	
	OptGamer() : Gamer () {}

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) const {}
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const {}

	virtual ~OptGamer(){}
};