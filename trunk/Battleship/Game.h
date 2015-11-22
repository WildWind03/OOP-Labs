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

public:

	Game(Gamer & g1, Gamer & g2, Field & g1Field, Field & g2Field);

	void begin();

	void placeShips(Gamer & g, Field & f);

	virtual void notify();

	~Game();
};
