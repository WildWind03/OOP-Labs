#include "MyFieldView.h"

MyFieldView::MyFieldView(const Field & myField, const ShotField & shots) : FieldView(myField, shots)
{

}

bool MyFieldView::isShot(const size_t h, const size_t w) const
{
	return shots.isMarked(h, w);
}

bool MyFieldView::isShip(const size_t h, const size_t w) const
{
	return myField.isShipOnCell(h, w);
}

MyFieldView::~MyFieldView()
{
	
}

CellState MyFieldView::getCellState(const size_t h, const size_t w) const
{
	bool isShot = shots.isMarked(h, w);

	bool isShip = myField.isShipOnCell(h, w);

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