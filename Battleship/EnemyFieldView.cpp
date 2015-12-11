#include "EnemyFieldView.h"

EnemyFieldView::EnemyFieldView(const Field & myField, const ShotField & shots) : FieldView(myField, shots)
{

}

bool EnemyFieldView::isExistDestrCellNear(size_t y, size_t x) const
{
	for (long long int i = y - 1; i <= y + 1; ++i)
	{
		for (long long int k = x - 1; k <= x + 1; ++k)
		{
			if (i >= 0 && k >= 0)
			{
				if (myField.isPointInField(i, k))
				{
					if (CellState::DESTROYED == getCellState(i, k))
					{
						return true;
					}
				}
			}
		}
	}

	return false;
}

bool EnemyFieldView::isExistDestrCellNear(size_t y, size_t x, size_t yExcept, size_t xExcept) const
{
	for (long long int i = y - 1; i <= y + 1; ++i)
	{
		for (long long int k = x - 1; k <= x + 1; ++k)
		{
			if (i >= 0 && k >= 0)
			{
				if (myField.isPointInField(i, k))
				{
					if (CellState::DESTROYED == getCellState(i, k))
					{
						if ((i != yExcept) && (k != xExcept)) 
						{
							return true;
						}
					}
				}
			}
		}
	}

	return false;
}

bool EnemyFieldView::isExistLearntDestrCellNear(const ShotPoint & p) const
{
	if (isExistDestrCellNear(p.getHeight(), p.getWidth()))
	{
		return true;
	}

	return false;
}

bool EnemyFieldView::isExistLearntDestrCellNear(const ShotPoint & p, const ShotPoint & exceptIt) const
{
	if (isExistDestrCellNear(p.getHeight(), p.getWidth(), exceptIt.getHeight(), exceptIt.getWidth()))
	{
		return true;
	}

	return false;
}


bool EnemyFieldView::isShot(const size_t y, const size_t x) const
{
	return shots.isMarked(y, x);
}

bool EnemyFieldView::isShip(const size_t y, const size_t x) const
{
	return myField.isShipOnCell(y, x);
}

EnemyFieldView::~EnemyFieldView()
{
	
}

CellState EnemyFieldView::getCellState(const size_t y, const size_t x) const
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
		return CellState::UNKNOWN;
	}

}