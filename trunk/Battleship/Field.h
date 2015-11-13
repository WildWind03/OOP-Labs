#pragma once
#include "Cell.h"
#include "Ship.h"
#include <vector>

class Field
{
	size_t height;
	size_t length;

	const size_t defaultHeight = 10;
	const size_t defaultLength = 10;
	const size_t defaultSize = 10;

	const string out_of_range_str = "Error: trying to get cell out of field";

	vector <Cell*> cells;
	
public:

	Field (size_t size = defaultValue);
	Field (size_t height = defaultHeight, size_t length = defaultLength); 

	size_t getLength() const;

	size_t getHeight() const;

	Cell* getCellByNum(size_t num);

	size_t getRandPos() const;

	bool isPosCorrectForShip(size_t pos, size_t size, bool vertOrHor);

	~Field();
}