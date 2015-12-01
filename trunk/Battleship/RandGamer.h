#pragma once

#include "Field.h"
#include "Gamer.h"
#include "ShipPoint.h"
#include "ShotPoint.h"
#include "myRand.h"

#include <cstdio>

class RandGamer : public Gamer
{
public:

	RandGamer();

	RandGamer (const RandGamer & g) = delete;
	RandGamer & operator=(const RandGamer & g) = delete;

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) const;
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const;

	virtual ~RandGamer();
};
