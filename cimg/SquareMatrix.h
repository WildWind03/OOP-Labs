#pragma once

#include "BaseMatrix.h"

#include <string>
#include <stdexcept>

class SquareMatrix : public BaseMatrix
{
protected:
	SquareMatrix(size_t width, size_t height) : BaseMatrix(width, height)
	{
		if (width != height)
		{
			throw std::invalid_argument("Error! Matrix is not square!");
		}
	}

public:
	virtual ~SquareMatrix()
	{

	}
};