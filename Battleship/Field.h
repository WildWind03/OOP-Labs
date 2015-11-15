#pragma once

#include "Cell.h"
#include "Ship.h"
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
	size_t length;

	const std::string out_of_range_str = "Error: trying to get cell out of field";

	std::vector <Cell*> cells;
	std::vector <Ship*> ships;

public:
	Field (size_t height, size_t length);

	void addShip(Ship *ship, bool isVertical, size_t pos);

	size_t getLength() const;

	size_t getHeight() const;

	size_t getSize() const;

	Cell* getCellByNum(size_t num);

	size_t getRandPos() const;

	bool isPosCorrectForShip(size_t pos, size_t size, bool vertOrHor);

	~Field();
};
