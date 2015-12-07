#include "Field.h"

Field::Field(const size_t height, const size_t width) : height(height), width(width)
{
	for (size_t i = 0; i < getSize(); ++i)
	{
		cells.push_back(nullptr);
	}
}

void Field::clear()
{
	for (size_t i = 0; i < ships.size(); ++i)
	{
		delete ships[i];
	}

	for (size_t i = 0; i < getSize(); ++i)
	{
		cells[i] = nullptr;
	}

	ships.clear();
}

bool Field::destroyShipOnCell(const size_t h, const size_t w)
{
	size_t pos = getPosFromPoint(h, w);

	Ship & myShip = *cells[pos];

	myShip.takeDamage();

	if (true == myShip.isDestroyed())
	{
		return true;
	}
	else
	{
		return false;
	}
}

bool Field::isAllShipsDestroyed() const
{
	for (size_t i = 0; i < ships.size(); ++i)
	{
		if (!ships[i] -> isDestroyed())
		{
			return false;
		}
	}

	return true;
}

bool Field::isPointInField(const size_t h, const size_t w) const
{
	if ((h >= getHeight()) || (w >= getWidth()))
	{
		return false;
	}

	return true;
}

bool Field::isShipOnCell(const size_t h, const size_t w) const
{
	size_t pos = getPosFromPoint(h, w);

	if (nullptr == cells[pos])
	{
		return false;
	}
	
	return true;
}

void Field::attachShip(Ship *ship, const ShipPoint & point)
{
	if (isPointInField(point.getHeight(), point.getWidth()) == false)
	{
		throw ImpossibleShipException(notInFieldStr);
	}

	if (isWholeShipOnField(ship -> getSize(), point) == false)
	{
		throw ImpossibleShipException(notWholeShipStr);
	}

	if (isShipCloseCellsFree(ship -> getSize(), point) == false)
	{
		throw ImpossibleShipException(neighboursShipStr);
	}

	ships.push_back(ship);

	if (true == point.isVertical())
	{
		for (size_t i = 0; i < ship -> getSize(); ++i)
		{
			size_t pos = getPosFromPoint(point.getHeight() + i, point.getWidth());
			cells[pos] = ship;
		}
	}
	else
	{
		for (size_t i = 0; i < ship -> getSize(); ++i)
		{
			size_t pos = getPosFromPoint(point.getHeight(), point.getWidth() + i);
			cells[pos] = ship;
		}
	}	
}

size_t Field::getSize() const
{
	return getHeight() * getWidth();
}

size_t Field::getHeight() const
{
	return height;
}

size_t Field::getWidth() const
{
	return width;
}

size_t Field::getPosFromPoint(const size_t h, const size_t w) const
{
	return h * getWidth() + w;
}

bool Field::isCloseCellsFree(const size_t h, const size_t w) const
{
	long long int h1 = h;
	long long int w1 = w;
	
	for (long long int i = h1 - 1; i <= h1 + 1; ++i)
	{
		for (long long int k = w1 - 1; k <= w1 + 1; ++k)
		{
			if (i >= 0 && k >= 0)
			{
				if (true == isPointInField(i, k))
				{
					if (true == isShipOnCell(i, k))
					{
						return false;
					}
				}
			}
		}
	}

	return true;
}

bool Field::isWholeShipOnField(const size_t sizeOfShip, const ShipPoint & point) const
{
	if (true == point.isVertical())
	{
		if (point.getHeight() + sizeOfShip > getHeight())
		{
			return false;
		}
	}
	else
	{
		if (point.getWidth() + sizeOfShip > getWidth())
		{
			return false;
		}
	}

	return true;
}

bool Field::isShipCloseCellsFree (const size_t sizeOfShip, const ShipPoint & point) const
{
	if (false == point.isVertical())
	{
		for (size_t i = 0; i < sizeOfShip; ++i)
		{
			if (false == isCloseCellsFree(point.getHeight(), point.getWidth() + i))
			{
				return false;
			}
		}
	}
	else
	{
		for (size_t i = 0; i < sizeOfShip; ++i)
		{		
			if (false == isCloseCellsFree(point.getHeight() + i, point.getWidth()))
			{
				return false;
			}
		}	
	}

	return true;
}

Field::~Field()
{
	for (size_t i = 0; i < ships.size(); ++i)
	{
		delete ships[i];
	}
}