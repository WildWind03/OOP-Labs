#include "Game.h"

Game::Game (Gamer & g1, Gamer & g2)
			: g1 (g1), g2(g2), countOfTurns(0), g1Cells(0), g2Cells(0)
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

bool Game::isFirstGamerTurn() const
{
	if (0 == countOfTurns % 2)
	{
		return true;
	}

	return false;
}

bool Game::isGameEnded() const
{
	if ((0 == g1Cells) || (0 == g2Cells))
	{
		return true;
	}
	else
	{
		return false;
	}
}

bool Game::isG1Won() const
{
	if (0 == g2Cells)
	{
		return true;
	}

	if (0 == g1Cells)
	{
		return false;
	}

	throw std::runtime_error(gameNotEndedStr);
}

void Game::onGameEnded()
{
	if (true == isG1Won())
	{
		g1.onGameEnded(true);
		g2.onGameEnded(false);
	}
	else
	{
		g1.onGameEnded(false);
		g2.onGameEnded(true);
	}
	throw std::runtime_error(gameEndedStr);
}

bool Game::makeShot(const Gamer & g, MyFieldView * myFieldV, EnemyFieldView * enemyFieldV, Field * enemyField, ShotField * myShots)
{
	ShotPoint p = g.getPointForShot(*myFieldV, *enemyFieldV);
	
	markShot(*myShots, p);
	
	if (true == enemyField -> isShipOnCell(p.getHeight(), p.getWidth()))
	{
		bool isDestroyed = enemyField -> destroyShipOnCell(p.getHeight(), p.getWidth());
		
		if (true == isDestroyed)
		{
			g.onRecieveShotState(ShotState::DESTROYED);
		}
		else
		{
			g.onRecieveShotState(ShotState::INJURED);
		}

		if (true == isFirstGamerTurn())
		{
			--g2Cells;
		}
		else
		{
			--g1Cells;
		}

		return true;
	}
	else
	{
		++countOfTurns;	

		g.onRecieveShotState(ShotState::MISSED);

		return false;

	}
}

void Game::makeTurn(const Gamer & g)
{
	while(true)
	{
		if (true == isGameEnded())
		{
			onGameEnded();

			break;
		}

		try
		{
			bool isInjured;

			if (true == isFirstGamerTurn())
			{
				isInjured = makeShot(g1, g1MyFieldView, g1EnemyFieldView, g2Field, g1Shots);
			}
			else
			{
				isInjured = makeShot(g2, g2MyFieldView, g2EnemyFieldView, g1Field, g2Shots);
			}

			if (false == isInjured)
			{
				break;
			}
		}
		catch(std::range_error & er)
		{
			g.onRecieveError(er);
			continue;
		}

	}
}

void Game::newGame()
{
	g1.onGameStarted();
	g2.onGameStarted();

	countOfTurns = 0;
	g1Cells = 0;
	g2Cells = 0;

	for (size_t i = 1; i <= maxSizeOfShip; ++i)
	{
		for (size_t k = i; k <= maxSizeOfShip; ++k)
		{
			g1Cells+=i;
			g2Cells+=i;
		}
	}

	g1Field -> clear();
	g2Field -> clear();
	g1Shots -> clear();
	g2Shots -> clear();

	placeShips(g2, *g2Field);
	placeShips(g1, *g1Field);

	beginGame();
}

void Game::beginGame()
{
	while (true)
	{
		try
		{
			makeTurn(g1);
			makeTurn(g2);
		}
		catch(const std::runtime_error & gameEnded)
		{
			break;
		}
	}
}

void Game::placeShips (const Gamer & g, Field  & f)
{
	Ship *myShip = nullptr;

	for (size_t i = 1; i <= maxSizeOfShip; ++i)
	{
		for (size_t k = i; k <= maxSizeOfShip; ++k)
		{
			myShip = new Ship(i);

			while(true)
			{
				try
				{
					if (true == isFirstGamerTurn())
					{
						ShipPoint p = g.getPointForShip(i, *g1MyFieldView);
						f.attachShip(myShip, p);
					}
					else
					{
						ShipPoint p = g.getPointForShip(i, *g2MyFieldView);
						f.attachShip(myShip, p);
					}

					break;
				}
				catch(std::range_error & er)
				{
					g.onRecieveError(er);
					
					continue;
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
		throw std::range_error(alreadyShot);
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