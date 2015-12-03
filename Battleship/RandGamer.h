#pragma once

#include "Field.h"
#include "Gamer.h"
#include "ShipPoint.h"
#include "ShotPoint.h"
#include "myRand.h"

#include <cstdio>
#include <vector>
#include <algorithm>
#include <string>

class RandGamer : public Gamer
{
	std::vector <ShotPoint> shots;
	std::vector <ShipPoint> ships;

	size_t shipCounter;
	size_t shotCounter;

	void fillShotList(size_t h, size_t w);
	void fillShipList(size_t h, size_t w);

public:

	RandGamer();

	RandGamer (const RandGamer & g) = delete;
	RandGamer & operator=(const RandGamer & g) = delete;

	virtual void onGameStarted(size_t hField, size_t wField) override;
	virtual void onGameEnded(bool isWon) override;
	virtual void onRecieveShotState(ShotState state, ShotPoint p) override;
	virtual void onRecieveResultOfPlacingShip(bool isPlaced) override;

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV);
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV);

	virtual ~RandGamer();
};
