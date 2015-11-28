#include "Controller.h"

Controller::Controller (GameConf  & conf)
{
	countOfRounds = conf.getCountRound();
	currentRound = 0;

	g1 = GamerFactory::CreateGamer(conf.getFPlayer());
	g2 = GamerFactory::CreateGamer(conf.getSPlayer());

	game = new Game(*g1, *g2);
}

void Controller::beginGame()
{
	game -> begin();
}

Controller::~Controller()
{
	delete(game);
	delete(g1);
	delete(g2);
}