#pragma once

#include "Ship.h"
#include "myRand.h"
#include "ShipPoint.h"
#include "Exceptions.h"

#include <iostream>
#include <string>
#include <vector>
#include <cstdio>
#include <random>
#include <ctime>
#include <limits>
#include <cstdlib>
#include <stdexcept>

class Field
{
	const size_t height;
	const size_t width;

	std::vector <Ship*> cells;
	std::vector <Ship*> ships;

	size_t getPosFromPoint(const size_t h, const size_t w) const;

	bool isCloseCellsFree(const size_t h, const size_t w) const;
	bool isShipCloseCellsFree(const size_t sizeOfShip, const ShipPoint & p) const;
	bool isWholeShipOnField(const size_t sizeOfShip, const ShipPoint & p) const;

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
	void attachShip(Ship *ship, const ShipPoint & p);

	size_t getWidth() const;
	size_t getHeight() const;
	size_t getSize() const;

	~Field();
};
