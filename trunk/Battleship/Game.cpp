#include "Game.h"

Game::Game (Gamer & g1, Gamer & g2, Field & g1Field, Field & g2Field, SimpleField & g1Shots, SimpleField & g2Shots)
			: g1 (g1), g2(g2), g1Field(g1Field), g2Field(g2Field), g1Shots(g1Shots), g2Shots(g2Shots)
{

}

void Game::begin()
{
	placeShips(g2, g2Field);

	//ConsoleView v;
	//v.paint(g2Field);

	placeShips(g1, g1Field);

	while (true)
	{
		g1.makeShot();
		g2.makeShot();
	}
}

void Game::placeShips (Gamer & g, Field  & f)
{
	Ship *myShip = nullptr;

	for (size_t i = 1; i < 5; ++i)
	{
		for (size_t k = i; k < 5; ++k)
		{
			while(true)
			{
				try
				{
					FieldPoint p = g.getPoint(i);

					myShip = new Ship(i);

					f.attachShip(myShip, p);

					break;
				}
				catch(std::range_error & er)
				{
					std::cout << "\n" << er.what() << std::endl;

					delete(myShip);

					continue;
				}
			}
		}
	}	
}

void Game::markShot(SimpleField & f)
{

}

void Game::notify()
{

}

Game::~Game()
{

}