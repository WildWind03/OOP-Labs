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

	const std::string initErrorStr = "Error! The gamer isn't initialized!";
	
    Gamer();

    Gamer & operator= (const Gamer & gamer) = delete;
    Gamer (const Gamer & gamer) = delete;

public:
	
	virtual void onRecieveShotState(const ShotState & state, const ShotPoint & prevShot);
	virtual void onGetStatistics(const Statistics & stat);
	virtual void onRecieveResultOfPlacingShip(bool isPlaced);
	virtual void onGameEnded(bool isWon);
	virtual void onGameStarted(size_t hField, size_t wField);
	virtual void onRecieveErrorString(const std::string & errorStr);

	bool isReadyToStart() const;

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) = 0;
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) = 0;

	virtual ~Gamer();
};
