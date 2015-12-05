#include "Game.h"

Game::Game (Gamer & g1, Gamer & g2)
			: g1 (g1), g2(g2), countOfTurns(0)
{
	g1Field = new Field(hField, wField);
	g2Field = new Field(hField, wField);

	g1Shots = new ShotField (hField, wField);
	g2Shots = new ShotField (hField, wField);

	g1EnemyFieldView = new EnemyFieldView(*g2Field, *g1Shots);
	g2EnemyFieldView = new EnemyFieldView(*g1Field, *g2Shots);

	g1MyFieldView = new MyFieldView(*g1Field, *g2Shots);
	g2MyFieldView = new MyFieldView(*g2Field, *g1Shots);
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
	if (g1Field -> isAllShipsDestroyed() || g2Field -> isAllShipsDestroyed())
	{
		return true;
	}

	return false;
}

void Game::onGameEnded(GamerNum winner)
{
	if (GamerNum::Gamer1 == winner)
	{
		g1.onGameEnded(true);
		g2.onGameEnded(false);
	}
	else
	{
		g1.onGameEnded(false);
		g2.onGameEnded(true);
	}

	throw GameEndedEvent();
}

ShotState Game::makeShot(Gamer & g, MyFieldView * myFieldV, EnemyFieldView * enemyFieldV, Field * enemyField, ShotField * myShots)
{
	ShotPoint p;
	
	try
	{
		p = g.getPointForShot(*myFieldV, *enemyFieldV);

		markShot(*myShots, p);
	}
	catch (const ImpossibleShotError & er)
	{
		g.onRecieveShotState(ShotState::ERROR, p);
		throw;
	}
	catch (const GameExitEvent & ex)
	{
		throw;
	}
	
	if (true == enemyField -> isShipOnCell(p.getHeight(), p.getWidth()))
	{
		bool isDestroyed = enemyField -> destroyShipOnCell(p.getHeight(), p.getWidth());

		if (true == isDestroyed)
		{
			g.onRecieveShotState(ShotState::DESTROYED, p);

			return ShotState::DESTROYED;
		}
		else
		{
			g.onRecieveShotState(ShotState::INJURED, p);

			return ShotState::INJURED;
		}
	}
	else
	{
		++countOfTurns;	

		g.onRecieveShotState(ShotState::MISSED, p);

		return ShotState::MISSED;

	}
}

void Game::makeTurn (Gamer & g)
{
	while(true)
	{
		try
		{
			ShotState st;
			GamerNum g = getCurrentTurnGamerNum();

			if (GamerNum::Gamer1 == g)
			{
				st = makeShot(g1, g1MyFieldView, g1EnemyFieldView, g2Field, g1Shots);
			}
			else
			{
				st = makeShot(g2, g2MyFieldView, g2EnemyFieldView, g1Field, g2Shots);
			}

			if (ShotState::MISSED == st)
			{
				break;
			}

			if (true == isGameEnded())
			{
				onGameEnded(getCurrentTurnGamerNum());
				break;
			}
		}
		catch(const ImpossibleShotError & er)
		{
			continue;
		}
		catch(const GameExitEvent & erExit)
		{
			throw erExit;
		}
	}
}

GamerNum Game::newGame()
{
	g1.onGameStarted(hField, wField);
	g2.onGameStarted(hField, wField);

	countOfTurns = 0;

	g1Field -> clear();
	g2Field -> clear();
	g1Shots -> clear();
	g2Shots -> clear();

	GamerNum winner;

	try
	{
		placeShips(g1, *g1Field, *g1MyFieldView);
		placeShips(g2, *g2Field, *g2MyFieldView);

		winner = beginGame();
	}
	catch(const GameExitEvent & erExit)
	{
		throw erExit;
	}

	return winner;
}

GamerNum Game::beginGame()
{
	while (true)
	{
		try
		{
			makeTurn(g1);
			makeTurn(g2);
		}
		catch(const GameEndedEvent & gameEnded)
		{
			break;
		}
		catch(const GameExitEvent & erExit)
		{
			throw erExit;
		}
	}

	return getCurrentTurnGamerNum();
}

void Game::placeShips (Gamer & g, Field  & f, MyFieldView & myFV)
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
					ShipPoint p = g.getPointForShip(i, myFV);

					f.attachShip(myShip, p);

					g.onRecieveResultOfPlacingShip(true);

					break;
				}
				catch(const ImpossibleShipError & er)
				{
					g.onRecieveResultOfPlacingShip(false);
				}
				catch(const GameExitEvent & erExit)
				{
					delete(myShip);
					throw erExit;
				}
			}
		}
	}	
}

void Game::markShot(ShotField & f, ShotPoint p)
{
	bool isBusy = f.isMarked (p.getHeight(), p.getWidth());

	if (true == isBusy)
	{
		throw ImpossibleShotError();
	}
	else
	{
		f.mark(p.getHeight(), p.getWidth());
	}

}

Game::~Game()
{
	delete(g1MyFieldView);
	delete(g2MyFieldView);

	delete(g1EnemyFieldView);
	delete(g2EnemyFieldView);
	
	delete(g1Field);
	delete(g2Field);

	delete(g1Shots);
	delete(g2Shots);
}