#pragma once

#include "Field.h"
#include "ShipPoint.h"
#include "MyFieldView.h"
#include "EnemyFieldView.h"
#include "ShotPoint.h"
#include "ShotState.h"
#include "Statistics.h"

#include <cstdio>


class Gamer
{
	
protected:
	
    Gamer()
    {
    	
    }

public:

	virtual void onRecieveError(const std::range_error & er)
	{

	}

	virtual void onRecieveShotState(ShotState state)
	{

	}

	virtual void onGetStatistics(const Statistics & stat)
	{

	}

	virtual void onGameEnded(bool isWon)
	{

	}

	virtual void onGameStarted()
	{
		
	}

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) const = 0;
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const = 0;

	virtual ~Gamer()
	{

	}
};
