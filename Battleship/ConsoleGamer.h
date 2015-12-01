#pragma once

#include "ConsoleView.h"
#include "Gamer.h"
#include "Field.h"
#include "ShipPoint.h"
#include "ShotPoint.h"

#include <cstdio>

class ConsoleGamer : public Gamer
{

	ConsoleView * view;

public:

	ConsoleGamer(const ConsoleGamer & g) = delete;
	ConsoleGamer & operator= (const ConsoleGamer & g) = delete;

	ConsoleGamer();

	virtual ShipPoint getPointForShip (const size_t sizeOfShip, const MyFieldView & myFieldV) const;
	virtual ShotPoint getPointForShot (const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const;
	
	virtual void onRecieveError(const std::range_error & er) override;
	virtual void onRecieveShotState(ShotState state) override;
	virtual void onGetStatistics(const Statistics & stat) override;
	virtual void onGameEnded(bool isWon) override;
	virtual void onGameStarted() override;

	virtual ~ConsoleGamer();
};
