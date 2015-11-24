#include "Controller.h"

Controller::Controller (GameConf  & conf)
{
	countOfRounds = conf.getCountRound();
	currentRound = 0;

	fField = new Field(10, 10);
	sField = new Field(10, 10);

	fShots = new SimpleField (10, 10);
	sShots = new SimpleField (10, 10);

	MyFieldView *fMyFieldView = new MyFieldView(*fField, *sShots);
	MyFieldView *sMyFieldView = new MyFieldView(*sField, *fShots);

	EnemyFieldView *fEnemyFieldView = new EnemyFieldView(*sField, *fShots);
	EnemyFieldView *sEnemyFieldView = new EnemyFieldView(*fField, *sShots);

	fGamer = GamerFactory::CreateGamer(conf.getFPlayer(), fMyFieldView, fEnemyFieldView);
	sGamer = GamerFactory::CreateGamer(conf.getSPlayer(), sMyFieldView, sEnemyFieldView);

	game = new Game(*fGamer, *sGamer, *fField, *sField, *fShots, *sShots);
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
	delete(fShots);
	delete(sShots);
}