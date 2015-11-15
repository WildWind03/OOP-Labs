#pragma once

#include "Ship.h"

class Cell
{
	enum state {FREE, BUSY, DESTROYED};

	state myState;

	Ship *myShip;

public:
	
	Cell();

	void addShip(Ship *myShip);
	
	bool isFree() const;

	void destroy();

	~Cell();
};