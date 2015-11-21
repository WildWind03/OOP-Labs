#pragma once

#include "Field.h"
#include "Gamer.h"
#include "FieldPoint.h"
#include <cstdio>

class RandGamer : public Gamer
{

    bool isVertical() const;

    FieldPoint getCorrectFieldPoint(size_t sizeOfShip);

public:

	RandGamer() = delete;

	RandGamer(Field *myField, Field *aField);

	void placeShips();
	void makeShot() {}

	~RandGamer();
};
