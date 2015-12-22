#pragma once

#include "Pixel.h"

#include <string>
#include <vector>

class BaseMatrix
{
protected:

	std::vector<std::vector<float>> matrix;

	BaseMatrix()
	{

	}

public:
	virtual float getPixel(size_t x, size_t y) const = 0;

	size_t getHeight() const
	{
		if (0 == getWidth())
		{
			return 0;
		}
		else
		{
			return matrix[0].size();
		}
	}

	size_t getWidth() const
	{
		matrix.size();
	}

	virtual ~BaseMatrix()
	{

	}
};