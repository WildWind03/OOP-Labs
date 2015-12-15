#pragma once

#include "BaseMatrix.h"

#include <stdexcept>
#include <string>
#include <vector>
#include <cmath>

class BlurMatrix : public BaseMatrix
{
	size_t height = 5;
	size_t width = 5;

	std::vector<std::vector<float>> matrix;

	const std::string OUT_OF_MATRIX_STR = "Error! Can't apply edge filter because of wrong input!";

public:
	BlurMatrix(float sigma);

	BlurMatrix() = delete;
	BlurMatrix(const BlurMatrix & blurMatrix) = delete;
	BlurMatrix & operator= (const BlurMatrix & blurMatrix) = delete;

	virtual float getPixel(size_t x, size_t y) const override;

	virtual size_t getWidth() const override;
	virtual size_t getHeight() const override;

	virtual ~BlurMatrix();
};