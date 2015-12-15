#pragma once

#include "BaseMatrix.h"

#include <stdexcept>
#include <string>
#include <vector>

class EdgeMatrix : public BaseMatrix
{
	int roundPixel = -1;
	int centrPixel = 4;

	size_t height = 3;
	size_t width = 3;

	std::vector<std::vector<int>> matrix;

	const std::string OUT_OF_MATRIX_STR = "Error! Can't apply edge filter because of wrong input!";

public:
	EdgeMatrix();

	EdgeMatrix(const EdgeMatrix & edgeMatrix) = delete;
	EdgeMatrix & operator= (const EdgeMatrix & edgeMatrix) = delete;

	virtual float getPixel(size_t x, size_t y) const override;

	virtual size_t getWidth() const override;
	virtual size_t getHeight() const override;

	virtual ~EdgeMatrix();
};