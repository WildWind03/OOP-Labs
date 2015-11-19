#include "Field.h"

Field::Field(size_t height, size_t length)
{
	this -> height = height;
	this -> length = length;

	for (size_t i = 0; i < height * length; ++i)
	{
		cells.push_back(new Cell());
	}
}

void Field::addShip(Ship *ship, bool isVertical, size_t pos)
{
	size_t cPos = pos;
	
	if (true == isVertical)
	{
		for (size_t i = 0; i < ship -> getSize(); ++i)
		{
			Cell *myCell = getCellByNum(cPos);

			myCell -> addShip(ship);

			cPos = cPos + getLength();
		}
	}
	else
	{
		for (size_t i = 0; i < ship -> getSize(); ++i)
		{
			Cell *myCell = getCellByNum(cPos);

			myCell -> addShip(ship);

			++cPos;
		}
	}	
}

size_t Field::getSize() const
{
	return getHeight() * getLength();
}

size_t Field::getHeight() const
{
	return height;
}

size_t Field::getLength() const
{
	return length;
}

size_t Field::getRandPos() const
{
	std::default_random_engine rng;	

	rng.seed(std::random_device()());

    std::uniform_int_distribution<size_t> dist_a_b(0, height * length);

    return dist_a_b(rng);
}

Cell* Field::getCellByNum(size_t num)
{
	if (num >= 0 && num < height * length)
	{
		return cells[num];
	}
	else
	{
		throw std::range_error (out_of_range_str);
	}
}

bool Field::isPosCorrectForShip(size_t pos, size_t size, bool vertOrHor)
{
	if (false == vertOrHor) //horizontal
	{
		if (pos % getHeight() + size > getLength())
		{
			return false;
		}

		for (size_t i = 0; i < size; ++i)
		{
			Cell* temp = getCellByNum(pos + i);

			if (false == temp -> isFree())
			{
				return false;
			}
		}

		return true;
	}
	else // vertical
	{
		if (pos + (size - 1) * getLength() > getSize())
		{
			return false;
		}

		for (size_t i = 0; i < size; ++i)
		{
			Cell* temp = getCellByNum(pos + i * length);
			
			if (false == temp -> isFree())
			{
				return false;
			}
		}

		return true;	
	}
}

std::string Field::getStateOfCell(size_t n)
{
	Cell *c = getCellByNum(n);
	return c -> getState();
}

Field::~Field()
{
	for (size_t i = 0; i < height * length; ++i)
	{
		delete(cells[i]);
	}
}