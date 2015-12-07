#include "Game.h"

Game::Game (Gamer & gamer1, Gamer & gamer2)
			: gamer1 (gamer1), gamer2(gamer2), countOfTurns(0)
{
	gamer1Field = new Field(hField, wField);
	gamer2Field = new Field(hField, wField);

	gamer1Shots = new ShotField (hField, wField);
	gamer2Shots = new ShotField (hField, wField);

	gamer1EnemyFieldView = new EnemyFieldView(*gamer2Field, *gamer1Shots);
	gamer2EnemyFieldView = new EnemyFieldView(*gamer1Field, *gamer2Shots);

	gamer1MyFieldView = new MyFieldView(*gamer1Field, *gamer2Shots);
	gamer2MyFieldView = new MyFieldView(*gamer2Field, *gamer1Shots);
}

GamerNum Game::getCurrentTurnGamerNum() const
{
	if (0 == countOfTurns % 2)
	{
		return GamerNum::Gamer1;
	}

	return GamerNum::Gamer2;
}

bool Game::isGameEnded() const
{
	if (gamer1Field -> isAllShipsDestroyed() || gamer2Field -> isAllShipsDestroyed())
	{
		return true;
	}

	return false;
}

void Game::onGameEnded()
{
	if (GamerNum::Gamer1 == getCurrentTurnGamerNum())
	{
		gamer1.onGameEnded(true);
		gamer2.onGameEnded(false);
	}
	else
	{
		gamer1.onGameEnded(false);
		gamer2.onGameEnded(true);
	}

	throw GameEndedException(gameEndedStr);
}

ShotState Game::makeShot(Gamer & gamer, MyFieldView * myFieldV, EnemyFieldView * enemyFieldV, Field * enemyField, ShotField * myShots)
{
	ShotPoint shotPoint;
	
	try
	{
		shotPoint = gamer.getPointForShot(*myFieldV, *enemyFieldV);

		markShot(*myShots, shotPoint);
	}
	catch (const ImpossibleShotException & er)
	{
		gamer.onRecieveShotState(ShotState::ERROR, shotPoint);
		gamer.onRecieveErrorString(er.what());
		throw;
	}
	catch (const GameExitException & ex)
	{
		throw;
	}
	
	if (true == enemyField -> isShipOnCell(shotPoint.getHeight(), shotPoint.getWidth()))
	{
		bool isDestroyed = enemyField -> destroyShipOnCell(shotPoint.getHeight(), shotPoint.getWidth());

		if (true == isDestroyed)
		{
			gamer.onRecieveShotState(ShotState::DESTROYED, shotPoint);

			return ShotState::DESTROYED;
		}
		else
		{
			gamer.onRecieveShotState(ShotState::INJURED, shotPoint);

			return ShotState::INJURED;
		}
	}
	else
	{
		++countOfTurns;	

		gamer.onRecieveShotState(ShotState::MISSED, shotPoint);

		return ShotState::MISSED;

	}
}

void Game::makeTurn (Gamer & gamer)
{
	while(true)
	{
		try
		{
			ShotState st;
			GamerNum gamerNum = getCurrentTurnGamerNum();

			if (GamerNum::Gamer1 == gamerNum)
			{
				st = makeShot(gamer1, gamer1MyFieldView, gamer1EnemyFieldView, gamer2Field, gamer1Shots);
			}
			else
			{
				st = makeShot(gamer2, gamer2MyFieldView, gamer2EnemyFieldView, gamer1Field, gamer2Shots);
			}

			if (ShotState::MISSED == st)
			{
				break;
			}

			if (isGameEnded())
			{
				onGameEnded();
				break;
			}
		}
		catch(const ImpossibleShotException & error)
		{
			continue;
		}
		catch(const GameExitException & erExit)
		{
			throw erExit;
		}
	}
}

GamerNum Game::newGame()
{
	gamer1.onGameStarted(hField, wField);
	gamer2.onGameStarted(hField, wField);

	countOfTurns = 0;

	gamer1Field -> clear();
	gamer2Field -> clear();
	gamer1Shots -> clear();
	gamer2Shots -> clear();

	GamerNum winnerGamerNum;

	try
	{
		placeShips(gamer1, *gamer1Field, *gamer1MyFieldView);
		placeShips(gamer2, *gamer2Field, *gamer2MyFieldView);

		winnerGamerNum = beginGame();
	}
	catch(const GameExitException & erExit)
	{
		throw erExit;
	}

	return winnerGamerNum;
}

GamerNum Game::beginGame()
{
	while (true)
	{
		try
		{
			makeTurn(gamer1);
			makeTurn(gamer2);
		}
		catch(const GameEndedException & gameEnded)
		{
			break;
		}
		catch(const GameExitException & erExit)
		{
			throw;
		}
	}

	return getCurrentTurnGamerNum();
}

void Game::placeShips (Gamer & gamer, Field  & field, MyFieldView & myFV)
{
	Ship * myShip = nullptr;

	for (size_t i = maxSizeOfShip; i >= 1; --i)
	{
		for (size_t k = i; k <= maxSizeOfShip; ++k)
		{
			myShip = new Ship(i);

			while(true)
			{
				try
				{
					ShipPoint shipPoint = gamer.getPointForShip(i, myFV);

					field.attachShip(myShip, shipPoint);

					gamer.onRecieveResultOfPlacingShip(true);

					break;
				}
				catch(const ImpossibleShipException & er)
				{
					gamer.onRecieveResultOfPlacingShip(false);
					gamer.onRecieveErrorString(er.what());
				}
				catch(const GameExitException & erExit)
				{
					delete(myShip);
					throw erExit;
				}
			}
		}
	}	
}

void Game::markShot(ShotField & field, const ShotPoint & shotPoint)
{
	bool isBusy = field.isMarked (shotPoint.getHeight(), shotPoint.getWidth());

	if (true == isBusy)
	{
		throw ImpossibleShotException(shotPointIsBusyStr);
	}
	else
	{
		field.mark(shotPoint.getHeight(), shotPoint.getWidth());
	}

}

Game::~Game()
{
	delete(gamer1MyFieldView);
	delete(gamer2MyFieldView);

	delete(gamer1EnemyFieldView);
	delete(gamer2EnemyFieldView);
	
	delete(gamer1Field);
	delete(gamer2Field);

	delete(gamer1Shots);
	delete(gamer2Shots);
}