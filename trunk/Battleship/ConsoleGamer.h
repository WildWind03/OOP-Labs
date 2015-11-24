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

	ConsoleGamer(MyFieldView * myFieldV, EnemyFieldView * enemyFieldV);

	FieldPoint getPoint(const size_t sizeOfShip);
	
	void makeShot();

	~ConsoleGamer();
};
