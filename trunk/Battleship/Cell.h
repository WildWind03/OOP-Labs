#pragma once

#include "Ship.h"

class Cell
{
	enum state {FREE, BUSY, DESTROYED};

	state myState;

	Ship *myShip;

	bool isFree() const;

public:
	
	Cell();

	void addShip(Ship *myShip);

	void destroy();

	~Cell();
};