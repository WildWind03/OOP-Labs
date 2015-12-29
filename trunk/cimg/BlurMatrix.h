#pragma once

#include "SquareMatrix.h"

#include <string>

class BlurMatrix : public SquareMatrix
{
	const std::string OUT_OF_MATRIX_STR = "Error! Can't apply blur filter because of wrong input!";

	const float maxSigma = 10;

public:
	BlurMatrix(float sigma, size_t size);

	BlurMatrix() = delete;
	BlurMatrix (BlurMatrix && blurMatrix) = default;
	BlurMatrix(const BlurMatrix & blurMatrix) = delete;
	BlurMatrix & operator= (const BlurMatrix & blurMatrix) = delete;

	virtual float getPixel(size_t x, size_t y) const override;

	virtual ~BlurMatrix();
};