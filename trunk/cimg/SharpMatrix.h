#pragma once

#include "BaseMatrix.h"

#include <stdexcept>
#include <string>
#include <vector>

class SharpMatrix : public BaseMatrix
{
	int roundPixel = -1;
	int centrPixel = 5;

	size_t height = 3;
	size_t width = 3;

	const std::string OUT_OF_MATRIX_STR = "Error! Can't apply sharp filter because of wrong input!";

public:
	SharpMatrix();
	SharpMatrix (SharpMatrix && sharpMatrix) = default;

	SharpMatrix(const SharpMatrix & sharpMatrix) = delete;
	SharpMatrix & operator= (const SharpMatrix & sharpMatrix) = delete;

	virtual float getPixel(size_t x, size_t y) const override;

	virtual ~SharpMatrix();
};