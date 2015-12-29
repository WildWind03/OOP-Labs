#pragma once

#include "SquareMatrix.h"

#include <string>

class SharpMatrix : public SquareMatrix
{
	const std::string OUT_OF_MATRIX_STR = "Error! Can't apply sharp filter because of wrong input!";

public:
	SharpMatrix(size_t size);
	
	SharpMatrix() = delete;
	SharpMatrix (SharpMatrix && sharpMatrix) = default;

	SharpMatrix(const SharpMatrix & sharpMatrix) = delete;
	SharpMatrix & operator= (const SharpMatrix & sharpMatrix) = delete;

	virtual float getPixel(size_t x, size_t y) const override;

	virtual ~SharpMatrix();
};