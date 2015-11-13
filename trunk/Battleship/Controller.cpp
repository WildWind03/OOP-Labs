#include "Controller.h"


void Controller::GetPlaceForShipCommand::execute()
{
	pos = ctrl -> getPlaceForShip();
}

size_t Controller::getPlaceForShip()
{
	size_t pos = view -> getPlaceForShip();

	return pos;
}

Controller::Controller (GameConf *conf)
{

	shipCmd = new GetPlaceForShipCommand(this);

	conf = conf;

	currentRound = 0;

	game = new Game(conf);

	view = new ConsoleView();
}

Controller::~Controller()
{
	delete(shipCmd);
	delete(view);
}