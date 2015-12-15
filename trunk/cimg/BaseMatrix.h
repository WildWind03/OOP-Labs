#pragma once

#include "Pixel.h"

#include <string>

class BaseMatrix
{
protected:
	BaseMatrix()
	{

	}

public:
	virtual float getPixel(size_t x, size_t y) const = 0;

	virtual size_t getHeight() const = 0;
	virtual size_t getWidth() const = 0;

	virtual ~BaseMatrix()
	{

	}
};