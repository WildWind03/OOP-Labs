#include "EdgeMatrix.h"

#include <stdexcept>
#include <vector>

EdgeMatrix::EdgeMatrix(size_t width, size_t height) : SquareMatrix(width, height)
{
	size_t centrW = width / 2;
	size_t centrH = height / 2;

	if (0 == width % 2 || 0 == height % 2)
	{
		throw std::invalid_argument("Error! Can't create edge matrix!");
	}

	for (size_t i = 0; i < width; ++i)
	{
		for (size_t k = 0; k < height; ++k)
		{
			if (k == centrH)
			{
				matrix[i][k] = -1;
			}
			else
			{
				if (i == centrW)
				{
					matrix[i][k] = -1;
				}
				else
				{
					matrix[i][k] = 0;
				}
			}

		}
	}

	matrix[centrW][centrH] = width + height - 2;
}

float EdgeMatrix::getPixel(size_t x, size_t y) const
{
	if (x > width - 1 || y > height - 1)
	{
		throw std::range_error(OUT_OF_MATRIX_STR);
	}

	return matrix[x][y];
}

EdgeMatrix::~EdgeMatrix()
{

}