#pragma once

#include "Gamer.h"
#include "Field.h"

class OptGamer : public Gamer
{

public:
	
	OptGamer(Field *myField, Field *aField) : Gamer (myField, aField) {}

	void placeShips(){}
	void makeShot(){}
};