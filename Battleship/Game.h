#pragma once

#include "GameConf.h"
#include "GamerFactory.h"
#include "Subject.h"
#include "Field.h"
#include "Ship.h"

#include <cstdio>
#include <string>

class Game : public Subject
{

	const std::string alreadyShot = "This cell has already shot";
	Gamer & g1;
	Gamer & g2;

	Field & g1Field;
	Field & g2Field;

	SimpleField & g1Shots;
	SimpleField & g2Shots;

	size_t countOfTurns;

	bool isFirstGamerTurn();

public:

	Game(Gamer & g1, Gamer & g2, Field & g1Field, Field & g2Field, SimpleField & g1Shots, SimpleField & g2Shots);

	void begin();

	void placeShips(Gamer & g, Field & f);
	void makeShot(Gamer & g);
	void markShot (SimpleField & f, SimplePoint p);

	virtual void notify();

	~Game();
};
