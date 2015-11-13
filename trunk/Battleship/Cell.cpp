#include "Cell.h"

Cell::Cell()
{
	myState = FREE;

	myShip = nullptr;	
}

void Cell::addShip(Ship *myShip)
{
	myShip = myShip;
	
	myState = BUSY;
}

void Cell::destroy()
{
	myState = DESTROYED;
}

bool isFree()
{
	if (nullptr == myShip)
	{
		return true;
	}
	else
	{
		return false;
	}
}

Cell::~Cell() {}