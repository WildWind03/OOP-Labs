#include "ConsoleGamer.h"

ShipPoint ConsoleGamer::getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV) const
{
	view -> paint(myFieldV);

	ShipPoint p = view -> getShipPoint(sizeOfShip);

	return p;	
}

ShotPoint ConsoleGamer::getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV) const
{
	view -> paint(myFieldV);
	view -> paint(enemyFieldV);

	ShotPoint p = view -> getShotPoint();
	return p;
}

void ConsoleGamer::onGameEnded(bool isWon)
{
	view -> printGameEndedStr(isWon);
	isReady = false;
}

void ConsoleGamer::onGameStarted(size_t hField, size_t wField)
{
	view -> printGameStartedStr();
	isReady = true;
}

void ConsoleGamer::onRecieveResultOfPlacingShip(bool isPlaced)
{
	if (false == isPlaced)
	{
		view -> printPlacingShipError();
	}
}

void ConsoleGamer::onRecieveShotState(ShotState state)
{
	view -> printShotState(state);
}

void ConsoleGamer::onGetStatistics(const Statistics & stat)
{
	view -> printStatistics(stat);
}

ConsoleGamer::ConsoleGamer() : Gamer()
{
	view = new ConsoleView();
}

ConsoleGamer::~ConsoleGamer()
{
	delete(view);
}
