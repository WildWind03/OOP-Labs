#include "SharpMatrix.h"

SharpMatrix::SharpMatrix()
{
	matrix.resize(width);
	
	for (size_t i = 0; i < width; ++i)
	{
		matrix[i].resize(height);
	}

	matrix[0][0] = 0;
	matrix[0][1] = roundPixel;
	matrix[0][2] = 0;
	matrix[1][0] = roundPixel;
	matrix[1][1] = centrPixel;
	matrix[1][2] = roundPixel;
	matrix[2][0] = 0;
	matrix[2][1] = roundPixel;
	matrix[2][2] = 0;;
}

float SharpMatrix::getPixel(size_t x, size_t y) const
{
	if (x > width - 1 || y > height - 1)
	{
		throw std::range_error(OUT_OF_MATRIX_STR);
	}

	return matrix[x][y];
}

size_t SharpMatrix::getWidth() const
{
	return width;
}

size_t SharpMatrix::getHeight() const
{
	return height;
}

SharpMatrix::~SharpMatrix()
{

}