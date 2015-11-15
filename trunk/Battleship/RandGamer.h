#pragma once

#include "Field.h"
#include "Gamer.h"
#include <cstdio>

class RandGamer : public Gamer
{

    bool vertOrHor() const;

public:

	RandGamer(Field *myField, Field *aField);

	void placeShips();
	void makeShot() {}

	~RandGamer();
};
