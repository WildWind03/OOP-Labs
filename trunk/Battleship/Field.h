#pragma once

#include "Ship.h"
#include "myRand.h"
#include "ShipPoint.h"

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

	const std::string wrongFiledSize = "Incorrect height of field";
	const std::string size_error_str = "A ship with such size can't be set there. Try again";
	const std::string out_of_range_str = "Error! Trying to get cell out of field";
	const std::string out_of_field_str = "Trying to place the ship out of the field";
	const std::string place_error_str = "There is another ship close. Try again";

	std::vector <Ship*> cells;
	std::vector <Ship*> ships;

	size_t getPosFromPoint(const size_t h, const size_t w) const;

	bool isPointInField(const size_t h, const size_t w) const;
	bool isCloseCellsFree(const size_t h, const size_t w) const;
	bool isShipCloseCellsFree(const size_t sizeOfShip, const ShipPoint & p) const;
	bool isWholeShipOnField(const size_t sizeOfShip, const ShipPoint & p) const;

public:

	Field() = delete;
	Field & operator= (const Field & f) = delete;
	Field(const Field & f) = delete;

	Field (const size_t height = 10, const size_t width = 10);

	bool isShipOnCell(const size_t h, const size_t w) const;

	bool destroyShipOnCell(const size_t h, const size_t w);

	void clear();
	void attachShip(Ship *ship, const ShipPoint & p);

	size_t getWidth() const;
	size_t getHeight() const;
	size_t getSize() const;

	~Field();
};
