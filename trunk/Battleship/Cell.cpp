#include "Cell.h"

Cell::Cell()
{
	myState = FREE;

	myShip = nullptr;	
}

void Cell::addShip(Ship *myShip)
{
	this -> myShip = myShip;
	
	this -> myState = BUSY;
}

void Cell::destroy()
{
	myState = DESTROYED;
}

std::string Cell::getState() const
{
	if (myState == FREE)
	{
		return fr;
	}

	if (myState == BUSY)
	{
		return bs;
	}

	return ds;
}

bool Cell::isFree() const
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