#include "BlurMatrix.h"

BlurMatrix::BlurMatrix(float sigma)
{
	if (0 == sigma)
	{
		throw std::invalid_argument(OUT_OF_MATRIX_STR);
	}

	matrix.resize(width);
	
	for (size_t i = 0; i < width; ++i)
	{
		matrix[i].resize(height);
	}

	int uc = 0;
	int vc = 0;

	float g = 0;
	float sum = 0;

	for(int u = 0; u < static_cast<int> (width); ++u) 
	{
	    for (int v = 0; v < static_cast<int> (height); ++v) 
	    {
		    uc = u - (width - 1) / 2;
		    vc = v - (height - 1) / 2;

		    g = exp(-(uc * uc + vc * vc) / (2 * sigma * sigma));

		    sum += g;
		    matrix[u][v] = g;
		}
	}

  	for(size_t u = 0; u < width; ++u) 
  	{
	    for(size_t v = 0; v < height; ++v) 
	    {
	      	matrix[u][v] /= sum;
	    }
	}
}


float BlurMatrix::getPixel(size_t x, size_t y) const
{
	if (x > width - 1 || y > height - 1)
	{
		throw std::range_error(OUT_OF_MATRIX_STR);
	}

	return matrix[x][y];
}

BlurMatrix::~BlurMatrix()
{

}