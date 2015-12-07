#pragma once

#include "GameConf.h"
#include "GamerFactory.h"
#include "Field.h"
#include "Ship.h"
#include "ShotState.h"
#include "Statistics.h"
#include "GameEndedException.h"
#include "GameExitException.h"
#include "ImpossibleShipException.h"
#include "ImpossibleShotException.h"

#include <cstdio>
#include <string>

enum class GamerNum {Gamer1, Gamer2};

class Game
{
	const std::string gameEndedStr = "The game is ended!";
	const std::string shotPointIsBusyStr = "The point is already shot!";

	const size_t maxSizeOfShip = 4;
	const size_t hField = 10;
	const size_t wField = 10;

	Gamer & gamer1;
	Gamer & gamer2;

	Field * gamer1Field;
	Field * gamer2Field;

	ShotField * gamer1Shots;
	ShotField * gamer2Shots;

	MyFieldView * gamer1MyFieldView;
	MyFieldView * gamer2MyFieldView;

	EnemyFieldView * gamer1EnemyFieldView;
	EnemyFieldView * gamer2EnemyFieldView;

	size_t countOfTurns;

	bool isGameEnded() const;

	void makeTurn(Gamer & gamer);
	void markShot (ShotField & shotField, const ShotPoint & shotPoint);
	void placeShips(Gamer & gamer, Field & field, MyFieldView & myFV);
	void onGameEnded();
	
	GamerNum beginGame();
	GamerNum getCurrentTurnGamerNum() const;

	ShotState makeShot(Gamer & gamer, MyFieldView * myFieldV, EnemyFieldView * enemyFieldV, Field * enemyField, ShotField * myShots);	


public:

	Game(Gamer & gamer1, Gamer & gamer2);

	Game() = delete;
	Game & operator= (const Game & gamer) = delete;
	Game (const Game & gamer) = delete;

	GamerNum newGame();

	~Game();
};
