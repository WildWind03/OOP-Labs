#include "Field.h"

Field::Field(size_t size = defaultValue)
{
	this -> size = size;

	for (size_t i = 0; i < size * size; ++i)
	{
		cells.push_back(new Cell());
	}
}

size_t Field::getHeight() const
{
	return height;
}

size_t Field::getLength() const
{
	return length;
}

size_t Field::getRandPos()
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
		return field -> cells[num];
	}
	else
	{
		throw std::out_of_range(out_of_range_str);
	}
}

bool Field::isPosCorrectForShip(size_t pos, size_t size, bool vertOrHor)
{
	Cell* myCell = getCellByNum(pos);

	if (true == vertOrHor)
	{
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
	else
	{
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

Field::~Field()
{
	for (size_t i = 0; i < size * size; ++i)
	{
		delete(cells[i]);
	}
}