#pragma once
#include "Cell.h"
#include <vector>

class Field
{
	size_t size;

	const size_t defaultValue = 10;

	vector <Cell*> cells;
	
public:

	Field (size_t size = defaultValue);

	~Field();
}