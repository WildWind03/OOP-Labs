#include "Game.h"

Game::Game (GameConf *conf)
{
	conf = conf;

	currentRound = 0;

	fGamer = GamerFactory.CreateGamer(conf.getFPlayer);
	sGamer = GamerFactory.CreateGamer(conf.getSPlayer);

	fField = new Field();
	sField = new Field();
	
	myView = new ConsoleView();
}

void Game::init()
{
	fGamer -> placeShips();
	sGamer -> placeShips();
}

void Game::begin()
{
	++currentRound;

	while(true)
	{
		fGamer -> makeShot();
		sGamer -> makeShot();
	}
}

Game::~Game()
{
	delete(fGamer);
	delete(sGamer);
	delete(fField);
	delete(sField);
}