#include "Field.h"

Field::Field(size_t size = defaultValue)
{
	this -> size = size;
	for (size_t i = 0; i < size * size; ++i)
	{
		cells.push_back(new Cell());
	}
}

Field::~Field()
{
	for (size_t i = 0; i < size * size; ++i)
	{
		delete(cells[i]);
	}
}