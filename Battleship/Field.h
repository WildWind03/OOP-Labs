#pragma once

#include "Ship.h"
#include "myRand.h"
#include "ShipPoint.h"
#include "ImpossibleShipException.h"

#include <iostream>
#include <string>
#include <vector>
#include <cstdio>
#include <random>
#include <ctime>
#include <limits>
#include <cstdlib>

class Field
{
	const std::string notInFieldStr = "The point is not in field!";
	const std::string notWholeShipStr = "The ship is too big to place there";
	const std::string neighboursShipStr = "There are some other ships near";

	const size_t height;
	const size_t width;

	std::vector <Ship*> cells;
	std::vector <Ship*> ships;

	size_t getPosFromPoint(const size_t h, const size_t w) const;

	bool isCloseCellsFree(const size_t h, const size_t w) const;
	bool isShipCloseCellsFree(const size_t sizeOfShip, const ShipPoint & point) const;
	bool isWholeShipOnField(const size_t sizeOfShip, const ShipPoint & point) const;

public:

	Field() = delete;
	Field & operator= (const Field & f) = delete;
	Field(const Field & f) = delete;

	Field (const size_t height = 10, const size_t width = 10);

	bool isShipOnCell(const size_t h, const size_t w) const;
	bool isPointInField(const size_t h, const size_t w) const;
	bool isAllShipsDestroyed() const;

	bool destroyShipOnCell(const size_t h, const size_t w);

	void clear();
	void attachShip(Ship *ship, const ShipPoint & point);

	size_t getWidth() const;
	size_t getHeight() const;
	size_t getSize() const;

	virtual ~Field();
};
