#pragma once

#include "GameConf.h"
#include "GamerFactory.h"
#include "Subject.h"
#include "Field.h"
#include <cstdio>

class Game : public Subject
{
	Gamer *g1;
	Gamer *g2;

public:

	Game(Gamer *g1, Gamer *g2);

	void begin();

	virtual void notify();

	~Game();
};
