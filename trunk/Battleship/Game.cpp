#include "Game.h"

Game::Game (Gamer *g1, Gamer *g2)
{
	this -> g1 = g1;
	this -> g2 = g2;
}

void Game::begin()
{
	g1 -> placeShips();
	g2 -> placeShips();

	while (true)
	{
		g1 -> makeShot();
		g2 -> makeShot();
	}
}

void Game::notify()
{

}

Game::~Game()
{

}