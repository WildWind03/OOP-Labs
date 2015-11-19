#pragma once

#include "ConsoleView.h"
#include "Gamer.h"
#include "Field.h"
#include "FieldPoint.h"
#include <cstdio>

class ConsoleGamer : public Gamer
{

	ConsoleView *view;

	FieldPoint getCorrectFieldPoint(size_t sizeOfShip);

public:

	ConsoleGamer() = delete;
	ConsoleGamer(ConsoleView *view, Field *myField, Field *aField);

	void placeShips();
	void makeShot();

	~ConsoleGamer();
};
