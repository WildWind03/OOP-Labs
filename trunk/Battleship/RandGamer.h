#pragma once

#include "Field.h"
#include "Gamer.h"
#include "FieldPoint.h"
#include "myRand.h"

#include <cstdio>

class RandGamer : public Gamer
{

public:

	RandGamer() = delete;

	RandGamer(Field & myField, Field & aField);

	FieldPoint getPoint(const size_t sizeOfShip);

	void makeShot() {}

	~RandGamer();
};
