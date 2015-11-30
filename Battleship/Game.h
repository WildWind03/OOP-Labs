#pragma once

#include "GameConf.h"
#include "GamerFactory.h"
#include "Field.h"
#include "Ship.h"
#include "ShotState.h"
#include "Statistics.h"

#include <cstdio>
#include <string>

class Game
{
	const std::string alreadyShot = "This cell has already destroyed";
	const std::string gameEndedStr = "Can't do next turn. The game is ended!";
	const std::string gameNotEndedStr = "Error! The game is not ended!";

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

	size_t g1Cells;
	size_t g2Cells;

	size_t countOfTurns;

	void onGameEnded();

	bool isFirstGamerTurn() const;
	bool isGameEnded() const;
	bool makeShot(const Gamer & g, MyFieldView * myFieldV, EnemyFieldView * enemyFieldV, Field * enemyField, ShotField * myShots);
	void beginGame();

public:

	Game(Gamer & g1, Gamer & g2);

	Game() = delete;
	Game & operator= (const Game & g) = delete;
	Game (const Game & g) = delete;

	void newGame();

	void placeShips(const Gamer & g, Field & f);
	void makeTurn(const Gamer & g);
	void markShot (ShotField & f, ShotPoint p);

	bool isG1Won() const;

	~Game();
};
