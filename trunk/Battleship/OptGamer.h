#pragma once

#include "Gamer.h"
#include "Field.h"
#include "ShipPoint.h"
#include "ShotPoint.h"
#include "myRand.h"

#include <vector>
#include <cstdio>
#include <algorithm>


class OptGamer : public Gamer
{
	enum class GamerState {EXPLORE, HIT} st;

	size_t shotCounter;

	std::vector <ShotPoint> stShots;

	ShotPoint getNextStandartShot() const;

	void fillShotList(size_t hField, size_t wField);

public:
	
	OptGamer();

	OptGamer (const OptGamer & g) = delete;
	OptGamer & operator= (const OptGamer & g) = delete;

	virtual void onGameStarted (size_t hField, size_t wField) override;
	virtual void onGameEnded(bool isWon) override;
	virtual void onRecieveShotState(ShotState state) override;

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) const;
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const;

	virtual ~OptGamer();
};