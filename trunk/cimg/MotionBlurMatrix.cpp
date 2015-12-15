#include "MotionBlurMatrix.h"

MotionBlurMatrix::MotionBlurMatrix()
{
	matrix.resize(width);
	
	for (size_t i = 0; i < width; ++i)
	{
		matrix[i].resize(height);
	}

	for (size_t i = 0; i < width; ++i)
	{
		for (size_t k = i; k < height; ++k)
		{
			if (i == k)
			{
				matrix[i][k] = 1;
			}
			else
			{
				matrix[i][k] = 0;
			}
		}
	}
}

float MotionBlurMatrix::getPixel(size_t x, size_t y) const
{
	if (x > width - 1 || y > height - 1)
	{
		throw std::range_error(OUT_OF_MATRIX_STR);
	}

	return matrix[x][y];
}

size_t MotionBlurMatrix::getWidth() const
{
	return width;
}

size_t MotionBlurMatrix::getHeight() const
{
	return height;
}

MotionBlurMatrix::~MotionBlurMatrix()
{

}