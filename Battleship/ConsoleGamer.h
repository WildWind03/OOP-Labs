#pragma once

#include "ConsoleView.h"
#include "Gamer.h"
#include "Field.h"
#include "FieldPoint.h"
#include <cstdio>

class ConsoleGamer : public Gamer
{

	ConsoleView *view;

public:

	ConsoleGamer() = delete;
	ConsoleGamer(const ConsoleGamer & g) = delete;
	ConsoleGamer & operator= (const ConsoleGamer & g) = delete;

	ConsoleGamer(Field & myField, Field & aField);

	FieldPoint getPoint(const size_t sizeOfShip);
	
	void makeShot();

	~ConsoleGamer();
};
