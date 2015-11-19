#include "Field.h"

Field::Field(size_t height, size_t width)
{
	this -> height = height;
	this -> width = width;

	for (size_t i = 0; i < height * width; ++i)
	{
		cells.push_back(new Cell());
	}
}

bool Field::isPointInField(size_t h, size_t w) const
{
	if ((h > getHeight()) || (w > getWidth()) || (h < 0) || (w < 0))
	{
		return false;
	}

	return true;
}

void Field::addShip(Ship *ship, const FieldPoint & p)
{
	if (true == p.isVertical())
	{
		for (size_t i = 0; i < ship -> getSize(); ++i)
		{
			Cell *myCell = getCellByNum(p.getHeight() + i, p.getWidth());
			myCell -> addShip(ship);
		}
	}
	else
	{
		for (size_t i = 0; i < ship -> getSize(); ++i)
		{
			Cell *myCell = getCellByNum(p.getHeight(), p.getWidth() + i);
			myCell -> addShip(ship);
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

size_t Field::fromHWToPos(size_t h, size_t w) const
{
	return h * getWidth() + w;
}

Cell* Field::getCellByNum(size_t h, size_t w)
{
	if (isPointInField(h,w))
	{
		return cells[h * getWidth() + w];
	}
	else
	{
		throw std::range_error (out_of_range_str);
	}
}

bool Field::isPosCorrectForShip(size_t sizeOfShip, const FieldPoint & p)
{
	if (false == isPointInField(p.getHeight(), p.getWidth()))
	{
		return false;
	}

	if (false == p.isVertical())
	{
		if (p.getWidth() + sizeOfShip > getWidth())
		{
			return false;
		}

		for (size_t i = 0; i < sizeOfShip; ++i)
		{
			Cell* temp = getCellByNum(p.getHeight(), p.getWidth() + i);

			if (false == temp -> isFree())
			{
				return false;
			}
		}

		return true;
	}
	else
	{
		if (p.getHeight() + sizeOfShip > getHeight())
		{
			return false;
		}

		for (size_t i = 0; i < sizeOfShip; ++i)
		{
			Cell* temp = getCellByNum(p.getHeight() + i, p.getWidth());
			
			if (false == temp -> isFree())
			{
				return false;
			}
		}

		return true;	
	}
}

size_t Field::getRand(size_t start, size_t end)
{
   	std::default_random_engine rng;

	rng.seed(std::random_device()());

   	std::uniform_int_distribution<int> dist_a_b(start, end);
   	
   	return dist_a_b(rng);
}

std::string Field::getStateOfCell(size_t h, size_t w)
{
	Cell *c = getCellByNum(h, w);

	return c -> getState();
}

Field::~Field()
{
	for (size_t i = 0; i < getSize(); ++i)
	{
		delete(cells[i]);
	}
}