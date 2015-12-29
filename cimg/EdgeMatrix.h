#pragma once

#include "SquareMatrix.h"

#include <string>

class EdgeMatrix : public SquareMatrix
{
	const std::string OUT_OF_MATRIX_STR = "Error! Can't apply edge filter because of wrong input!";

public:
	EdgeMatrix(size_t size);

	EdgeMatrix() = delete;
	EdgeMatrix (EdgeMatrix && edgeMatrix) = default;

	EdgeMatrix(const EdgeMatrix & edgeMatrix) = delete;
	EdgeMatrix & operator= (const EdgeMatrix & edgeMatrix) = delete;
	
	virtual float getPixel(size_t x, size_t y) const override;

	virtual ~EdgeMatrix();
};