#pragma once

#include "Field.h"
#include "ShipPoint.h"
#include "MyFieldView.h"
#include "EnemyFieldView.h"
#include "ShotPoint.h"
#include "ShotState.h"

#include <cstdio>


class Gamer
{
	
protected:
	
    Gamer()
    {
    	
    }

public:

	virtual void recieveError(const std::range_error & er) const
	{

	}

	virtual void recieveShotState(ShotState state) const
	{

	}

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) const = 0;
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const = 0;

	virtual ~Gamer()
	{

	}
};
