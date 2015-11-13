#pragma once
#include "Field.h"
#include "Cell.h"

class Ship
{
	size_t size;
	size_t destrSize;

public:
	
	Ship(bool isVertical, size_t size, size_t pos, Field *field);

	size_t getSize() const;

	size_t getDestrSize() const;

	bool isDestroyed() const;

	~Ship();
};