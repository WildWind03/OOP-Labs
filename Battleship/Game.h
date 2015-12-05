#pragma once

#include "GameConf.h"
#include "GamerFactory.h"
#include "Field.h"
#include "Ship.h"
#include "ShotState.h"
#include "Statistics.h"
#include "Exceptions.h"

#include <cstdio>
#include <string>

enum class GamerNum {Gamer1, Gamer2};

class Game
{
	const size_t maxSizeOfShip = 4;
	const size_t hField = 10;
	const size_t wField = 10;

	Gamer & g1;
	Gamer & g2;

	Field * g1Field;
	Field * g2Field;

	ShotField * g1Shots;
	ShotField * g2Shots;

	MyFieldView * g1MyFieldView;
	MyFieldView * g2MyFieldView;

	EnemyFieldView * g1EnemyFieldView;
	EnemyFieldView * g2EnemyFieldView;

	size_t countOfTurns;

	bool isGameEnded() const;

	void makeTurn(Gamer & g);
	void markShot (ShotField & f, ShotPoint p);
	void placeShips(Gamer & g, Field & f, MyFieldView & myFV);
	void onGameEnded(GamerNum winner);
	
	GamerNum beginGame();
	GamerNum getCurrentTurnGamerNum() const;

	ShotState makeShot(Gamer & g, MyFieldView * myFieldV, EnemyFieldView * enemyFieldV, Field * enemyField, ShotField * myShots);	


public:

	Game(Gamer & g1, Gamer & g2);

	Game() = delete;
	Game & operator= (const Game & g) = delete;
	Game (const Game & g) = delete;

	GamerNum newGame();

	~Game();
};
