#pragma once

#include "GameConf.h"
#include "GamerFactory.h"
#include "Field.h"
#include "Ship.h"
#include "ShotState.h"

#include <cstdio>
#include <string>

class Game
{
	const std::string alreadyShot = "This cell has already destroyed";

	Gamer & g1;
	Gamer & g2;

	Field * g1Field;
	Field * g2Field;

	SimpleField * g1Shots;
	SimpleField * g2Shots;

	MyFieldView * g1MyFieldView;
	MyFieldView * g2MyFieldView;

	EnemyFieldView * g1EnemyFieldView;
	EnemyFieldView * g2EnemyFieldView;

	size_t countOfTurns;

	bool isFirstGamerTurn() const;

public:

	Game(Gamer & g1, Gamer & g2);

	Game() = delete;
	Game & operator= (const Game & g) = delete;
	Game (const Game & g) = delete;

	void begin();

	void placeShips(const Gamer & g, Field & f);
	void makeShot(const Gamer & g);
	void markShot (SimpleField & f, ShotPoint p);

	~Game();
};
