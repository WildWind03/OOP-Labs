#include "ConsoleGamer.h"

ShipPoint ConsoleGamer::getPointForShip(const size_t sizeOfShip, const MyFieldView & myFieldV)
{
	if (false == isReadyToStart())
	{
		throw std::runtime_error(initError);
	}
	
	view -> paint(myFieldV);

	try
	{
		ShipPoint p = view -> getShipPoint(sizeOfShip);
		return p;	
	}
	catch (const GameExitEvent & er)
	{
		throw;
	}
}

ShotPoint ConsoleGamer::getPointForShot(const MyFieldView & myFieldV, const EnemyFieldView & enemyFieldV)
{
	if (false == isReadyToStart())
	{
		throw std::runtime_error(initError);
	}
	
	view -> paint(myFieldV);
	view -> paint(enemyFieldV);

	ShotPoint p;

	try
	{
		p = view -> getShotPoint();
	}

	catch (const GameExitEvent & er)
	{
		throw;
	}

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

void ConsoleGamer::onRecieveShotState(const ShotState & state, const ShotPoint & p)
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
