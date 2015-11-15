#pragma once

#include "ConsoleView.h"
#include "Gamer.h"
#include "Field.h"
#include "Point.h"
#include <cstdio>

class ConsoleGamer : public Gamer
{

	ConsoleView *view;

	bool checkPlace(size_t pos);

	BasePoint getPlaceForShip(size_t size);

	size_t getPosByPoint(const Point & p) const;

	bool checkPlace(size_t pos, size_t size, bool isVert);

public:

	ConsoleGamer() = delete;
	ConsoleGamer(ConsoleView *view, Field *myField, Field *aField);

	void placeShips();
	void makeShot();

	~ConsoleGamer();
};
