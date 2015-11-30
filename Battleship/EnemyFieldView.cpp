#include "EnemyFieldView.h"

EnemyFieldView::EnemyFieldView(const Field & myField, const ShotField & shots) : FieldView(myField, shots)
{

}

bool EnemyFieldView::isShot(const size_t h, const size_t w) const
{
	return shots.isMarked(h, w);
}

bool EnemyFieldView::isShip(const size_t h, const size_t w) const
{
	return myField.isShipOnCell(h, w);
}

EnemyFieldView::~EnemyFieldView()
{
	
}

CellState EnemyFieldView::getCellState(const size_t h, const size_t w) const
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
		return CellState::UNKNOWN;
	}

}