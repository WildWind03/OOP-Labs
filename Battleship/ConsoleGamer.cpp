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

void ConsoleGamer::onGameEnded(bool isWon) const
{
	view -> printGameEndedStr(isWon);
}

void ConsoleGamer::onGameStarted() const
{
	view -> printGameStartedStr();
}

void ConsoleGamer::onRecieveError(const std::range_error & er)  const
{
	view -> printError(er);
}

void ConsoleGamer::onRecieveShotState(ShotState state) const
{
	view -> typeShotState(state);
}

void ConsoleGamer::onGetStatistics(const Statistics & stat) const
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
