#include "EnemyFieldView.h"

EnemyFieldView::EnemyFieldView(const Field & myField, const ShotField & shots) : FieldView(myField, shots)
{

}

bool EnemyFieldView::isExistDestrCellNear(size_t h, size_t w) const
{
	long long int h1 = h;
	long long int w1 = w;
	
	for (long long int i = h1 - 1; i <= h1 + 1; ++i)
	{
		for (long long int k = w1 - 1; k <= w1 + 1; ++k)
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

bool EnemyFieldView::isExistDestrCellNear(size_t h, size_t w, size_t hExcept, size_t wExcept) const
{
	long long int h1 = h;
	long long int w1 = w;
	
	for (long long int i = h1 - 1; i <= h1 + 1; ++i)
	{
		for (long long int k = w1 - 1; k <= w1 + 1; ++k)
		{
			if (i >= 0 && k >= 0)
			{
				if (myField.isPointInField(i, k))
				{
					if (CellState::DESTROYED == getCellState(i, k))
					{
						if ((i != hExcept) && (w != wExcept)) 
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