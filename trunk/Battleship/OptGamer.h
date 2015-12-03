#pragma once

#include "Gamer.h"
#include "Field.h"
#include "ShipPoint.h"
#include "ShotPoint.h"
#include "myRand.h"
#include "ConsoleView.h"

#include <vector>
#include <cstdio>
#include <algorithm>


class OptGamer : public Gamer
{
	enum class GamerState {EXPLORE, HIT} st;

	size_t shotCounter;

	std::vector <ShotPoint> stShots;
	std::vector <ShotPoint> injured;
	std::vector <ShotPoint> nextShots;

	ShotPoint getNextStandartShot();
	ShotPoint getNextHitShot();

	bool isInjVert() const;
	bool isPossibleToBeShipThere(const ShotPoint & p, const EnemyFieldView & v) const;

	size_t getMaxWidthInj() const;
	size_t getMaxHeightInj() const;
	size_t getMinWidthInj() const;
	size_t getMinHeightInj() const;

	void fillNextShotsListNoOrient(size_t hField, size_t wField);
	void fillNextShotsListOrient(size_t hField, size_t wField);
	void fillNextShots(size_t hField, size_t wField);
	void fillStShotsList(size_t hField, size_t wField);

public:
	
	OptGamer();

	OptGamer (const OptGamer & g) = delete;
	OptGamer & operator= (const OptGamer & g) = delete;

	virtual void onGameStarted (size_t hField, size_t wField) override;
	virtual void onGameEnded(bool isWon) override;
	virtual void onRecieveShotState(ShotState state, ShotPoint p) override;

	virtual ShipPoint getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV);
	virtual ShotPoint getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV);

	virtual ~OptGamer();
};