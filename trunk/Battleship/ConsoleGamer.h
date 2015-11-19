#pragma once

#include "ConsoleView.h"
#include "Gamer.h"
#include "Field.h"
#include "Point.h"
#include <cstdio>

class ConsoleGamer : public Gamer
{

	ConsoleView *view;

	bool checkPoint(const Point & p) const;

	BasePoint getPlaceForShip(size_t size);

	size_t getPosByPoint(const Point & p) const;

	bool checkPlace(size_t size, const Point & p);

public:

	ConsoleGamer() = delete;
	ConsoleGamer(ConsoleView *view, Field *myField, Field *aField);

	void placeShips();
	void makeShot();

	~ConsoleGamer();
};
