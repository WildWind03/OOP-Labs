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

	bool isReady;

	const std::string initError = "Error! The gamer isn't initialized!";
	
    Gamer()
    {
    	isReady = false;
    }

public:
	
	virtual void onRecieveShotState(ShotState state, ShotPoint p)
	{

	}

	virtual void onGetStatistics(const Statistics & stat)
	{

	}

	virtual void onRecieveResultOfPlacingShip(bool isPlaced)
	{

	}

	virtual void onGameEnded(bool isWon)
	{
		isReady = false;
	}

	virtual void onGameStarted(size_t hField, size_t wField)
	{
		isReady = true;
	}

	bool isReadyToStart() const
	{
		return isReady;
	}

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) = 0;
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) = 0;

	virtual ~Gamer()
	{

	}
};
