#include "Game.h"

Game::Game (Gamer & g1, Gamer & g2)
			: g1 (g1), g2(g2), countOfTurns(0)
{
	g1Field = new Field(10, 10);
	g2Field = new Field(10, 10);

	g1Shots = new SimpleField (10, 10);
	g2Shots = new SimpleField (10, 10);

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

void Game::makeShot(const Gamer & g)
{
	while(true)
	{
		try
		{
			if (true == isFirstGamerTurn())
			{
				ShotPoint p = g.getPointForShot(*g1MyFieldView, *g1EnemyFieldView);
				markShot(*g1Shots, p);

				if (true == g2Field -> isShipOnCell(p.getHeight(), p.getWidth()))
				{
					g2Field -> destroyShipOnCell(p.getHeight(), p.getWidth());

					g1.recieveShotState(ShotState::INJURED);

					continue;
				}
				else
				{
					++countOfTurns;	

					g1.recieveShotState(ShotState::MISSED);

				}
			}
			else
			{
				ShotPoint p = g.getPointForShot(*g2MyFieldView, *g2EnemyFieldView);
				markShot(*g2Shots, p);

				if (true == g1Field -> isShipOnCell(p.getHeight(), p.getWidth()))
				{
					g1Field -> destroyShipOnCell(p.getHeight(), p.getWidth());

					g2.recieveShotState(ShotState::INJURED);

					continue;
				}
				else
				{
					++countOfTurns;	
					
					g2.recieveShotState(ShotState::MISSED);
				}
			}

			break;
		}
		catch(std::range_error & er)
		{
			g.recieveError(er);

			continue;
		}
	}
}

void Game::begin()
{
	placeShips(g2, *g2Field);
	placeShips(g1, *g1Field);

	while (true)
	{
		makeShot(g1);
		makeShot(g2);
	}
}

void Game::placeShips (const Gamer & g, Field  & f)
{
	Ship *myShip = nullptr;

	for (size_t i = 1; i < 5; ++i)
	{
		for (size_t k = i; k < 5; ++k)
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
					g.recieveError(er);
					
					continue;
				}
			}
		}
	}	
}

void Game::markShot(SimpleField & f, ShotPoint p)
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
	delete(g1Field);
	delete(g2Field);

	delete(g1Shots);
	delete(g2Shots);

	delete(g1MyFieldView);
	delete(g2MyFieldView);

	delete(g1EnemyFieldView);
	delete(g2EnemyFieldView);
}