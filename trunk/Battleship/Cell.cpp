#include "Cell.h"

Cell::Cell()
{
	myState = CellState::FREE;

	myShip = nullptr;	
}

void Cell::addShip(Ship *myShip)
{
	this -> myShip = myShip;
	
	this -> myState = CellState::BUSY;
}

void Cell::destroy()
{
	myState = CellState::DESTROYED;
}

CellState Cell::getState() const
{
	return myState;
}

bool Cell::isFree() const
{
	if (CellState::FREE == myState)
	{
		return true;
	}
	
	return false;
}

bool Cell::isBusy() const
{
	if (CellState::BUSY == myState)
	{
		return true;
	}

	return false;
}

Cell::~Cell() {}