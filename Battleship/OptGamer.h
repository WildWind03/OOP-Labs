#pragma once

#include "Gamer.h"
#include "Field.h"
#include "ShipPoint.h"
#include "ShotPoint.h"
#include "myRand.h"


class OptGamer : public Gamer
{

public:
	
	OptGamer();

	OptGamer (const OptGamer & g) = delete;
	OptGamer & operator= (const OptGamer & g) = delete;

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) const;
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const;

	virtual ~OptGamer();
};