#pragma once
#include "Field.h"

class RandGamer : public Gamer
{
	
public:

	RandGamer();

	void placeShips(Field *field);
	void makeShot();
};