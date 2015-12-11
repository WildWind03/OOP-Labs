#include "MyFieldView.h"

MyFieldView::MyFieldView(const Field & myField, const ShotField & shots) : FieldView(myField, shots)
{

}

bool MyFieldView::isShot(const size_t y, const size_t x) const
{
	return shots.isMarked(y, x);
}

bool MyFieldView::isShip(const size_t y, const size_t x) const
{
	return myField.isShipOnCell(y, x);
}

MyFieldView::~MyFieldView()
{
	
}

CellState MyFieldView::getCellState(const size_t y, const size_t x) const
{
	bool isShot = shots.isMarked(y, x);

	bool isShip = myField.isShipOnCell(y, x);

	if (true == isShot)
	{
		if (true == isShip)
		{
			return CellState::DESTROYED;
		}
		else
		{
			return CellState::MISSED;
		}
	}
	else
	{
		if (true == isShip)
		{
			return CellState::SHIP;
		}
		else
		{
			return CellState::FREE;
		}
	}

}