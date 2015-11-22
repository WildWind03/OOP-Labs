#include "Controller.h"

Controller::Controller (GameConf *conf)
{
	countOfRounds = conf -> getCountRound();
	currentRound = 0;

	fField = new Field(10, 10);
	sField = new Field(10, 10);
	fFieldAttack = new Field (10, 10);
	sFieldAttack = new Field (10, 10);

	fGamer = GamerFactory::CreateGamer(conf -> getFPlayer(), *fField, *fFieldAttack);
	sGamer = GamerFactory::CreateGamer(conf -> getSPlayer(), *sField, *sFieldAttack);

	game = new Game(*fGamer, *sGamer, *fField, *sField);
}

void Controller::beginGame()
{
	game -> begin();
}

Controller::~Controller()
{
	delete(view);
	delete(game);
	delete(fGamer);
	delete(sGamer);
	delete(sField);
	delete(fField);
	delete(fFieldAttack);
	delete(sFieldAttack);
}