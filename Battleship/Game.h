#pragma once

#include "GameConf.h"
#include "GamerFactory.h"
#include "Subject.h"
#include "Field.h"
#include "Ship.h"
#include <cstdio>

class Game : public Subject
{
	Gamer & g1;
	Gamer & g2;

	Field & g1Field;
	Field & g2Field;

	SimpleField & g1Shots;
	SimpleField & g2Shots;

public:

	Game(Gamer & g1, Gamer & g2, Field & g1Field, Field & g2Field, SimpleField & g1Shots, SimpleField & g2Shots);

	void begin();

	void placeShips(Gamer & g, Field & f);
	void markShot (SimpleField & f);

	virtual void notify();

	~Game();
};
