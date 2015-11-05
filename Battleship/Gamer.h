#pragma once

class Gamer
{

	Field *enemyField;

public:

	Gamer()=0;

	virtual void placeShips()=0;

	virtual void makeShot()=0;
};