#pragma once

#include "Gamer.h"
#include "Field.h"
#include "FieldPoint.h"

class OptGamer : public Gamer
{

public:
	
	OptGamer(Field & myField, Field & aField) : Gamer (myField, aField) {}

	FieldPoint getPoint(const size_t sizeOfShip) {}
	void makeShot(){}
};