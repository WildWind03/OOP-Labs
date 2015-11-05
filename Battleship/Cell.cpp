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

Cell::~Cell() {}