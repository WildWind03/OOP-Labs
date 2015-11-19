#pragma once

#include "Cell.h"
#include "Ship.h"
#include "FieldPoint.h"
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
	size_t height;
	size_t width;

	const std::string out_of_range_str = "Error: trying to get cell out of field";

	std::vector <Cell*> cells;
	std::vector <Ship*> ships;

	Cell * getCellByNum(size_t h, size_t w);

	size_t fromHWToPos(size_t h, size_t w) const;

	bool isPointInField(size_t h, size_t w) const;

public:
	Field (size_t height, size_t width);

	void addShip(Ship *ship, const FieldPoint & p);

	size_t getWidth() const;

	size_t getHeight() const;

	size_t getSize() const;

	static size_t getRand(size_t start, size_t end);

	std::string getStateOfCell(size_t h, size_t w);

	bool isPosCorrectForShip(size_t sizeOfShip, const FieldPoint & p);

	~Field();
};
