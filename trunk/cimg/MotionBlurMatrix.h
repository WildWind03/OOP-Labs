#pragma once

#include "BaseMatrix.h"

#include <stdexcept>
#include <string>
#include <vector>

class MotionBlurMatrix : public BaseMatrix
{
	size_t height = 7;
	size_t width = 7;

	std::vector<std::vector<int>> matrix;

	const std::string OUT_OF_MATRIX_STR = "Error! Can't apply motion-blur filter because of wrong input!";

public:
	MotionBlurMatrix();

	MotionBlurMatrix(const MotionBlurMatrix & motionBlurMatrix) = delete;
	MotionBlurMatrix & operator= (const MotionBlurMatrix & motionBlurMatrix) = delete;

	virtual float getPixel(size_t x, size_t y) const override;

	virtual size_t getWidth() const override;
	virtual size_t getHeight() const override;

	virtual ~MotionBlurMatrix();
};