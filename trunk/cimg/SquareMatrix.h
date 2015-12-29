#pragma once

#include "BaseMatrix.h"

#include <string>
#include <stdexcept>

class SquareMatrix : public BaseMatrix
{
protected:
	SquareMatrix(size_t size) : BaseMatrix(size, size)
	{
	}

public:
	virtual ~SquareMatrix()
	{

	}
};