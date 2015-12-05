#pragma once

#include "ConsoleView.h"
#include "Gamer.h"
#include "Field.h"
#include "ShipPoint.h"
#include "ShotPoint.h"
#include "Exceptions.h"

#include <cstdio>

class ConsoleGamer : public Gamer
{

	ConsoleView * view;

public:

	ConsoleGamer(const ConsoleGamer & g) = delete;
	ConsoleGamer & operator= (const ConsoleGamer & g) = delete;

	ConsoleGamer();

	virtual ShipPoint getPointForShip (const size_t sizeOfShip, const MyFieldView & myFieldV);
	virtual ShotPoint getPointForShot (const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV);
	
    virtual void onRecieveResultOfPlacingShip(bool isPlaced) override;
	virtual void onRecieveShotState(const ShotState & state, const ShotPoint & p) override;
	virtual void onGetStatistics(const Statistics & stat) override;
	virtual void onGameEnded(bool isWon) override;
	virtual void onGameStarted(size_t hField, size_t wField) override;

	virtual ~ConsoleGamer();
};
