#pragma once

#include "ConsoleView.h"
#include "Gamer.h"
#include "Field.h"
#include "ShipPoint.h"
#include "ShotPoint.h"

#include <cstdio>

class ConsoleGamer : public Gamer
{

	ConsoleView *view;

public:

	ConsoleGamer(const ConsoleGamer & g) = delete;
	ConsoleGamer & operator= (const ConsoleGamer & g) = delete;

	ConsoleGamer();

	virtual ShipPoint getPointForShip (const size_t sizeOfShip, const MyFieldView & myFieldV) const;
	virtual ShotPoint getPointForShot (const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const;
	
	virtual void recieveError(const std::range_error & er) const override;
	virtual void recieveShotState(ShotState state) const override;

	virtual ~ConsoleGamer();
};
