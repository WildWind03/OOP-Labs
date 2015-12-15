#include "BlurMatrix.h"

BlurMatrix::BlurMatrix(float sigma)
{
	matrix.resize(width);
	
	for (size_t i = 0; i < width; ++i)
	{
		matrix[i].resize(height);
	}

	int uc, vc;
	float g, sum;

	sum = 0;

	for(int u = 0; u < width; ++u) 
	{
	    for (int v = 0; v < height; ++v) 
	    {
		    uc = u - (width - 1) / 2;
		    vc = v - (height - 1) / 2;

		    g = exp(-(uc * uc + vc * vc) / (2 * sigma * sigma));

		    sum += g;
		    matrix[u][v] = g;
		}
	}

  	for(int u = 0; u < width; ++u) 
  	{
	    for(int v = 0; v < height; ++v) 
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

size_t BlurMatrix::getWidth() const
{
	return width;
}

size_t BlurMatrix::getHeight() const
{
	return height;
}

BlurMatrix::~BlurMatrix()
{

}