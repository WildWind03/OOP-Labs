#pragma once

#include "Ship.h"
#include "CellState.h"

#include <string>
#include <iostream>

class Cell
{
	CellState myState;

	Ship *myShip;

public:
	
	Cell();

	Cell (const Cell & c) = delete;
	Cell & operator=(const Cell & c) = delete;

	void addShip(Ship *myShip);
	
	bool isFree() const;
	bool isBusy() const;

	CellState getState() const; 

	void destroy();

	~Cell();
};