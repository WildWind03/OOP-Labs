#pragma once

#include "Pixel.h"

#include <string>
#include <vector>
#include <stdexcept>

class BaseMatrix
{
protected:
	const size_t width;
	const size_t height;

	std::vector<std::vector<float>> matrix;

	BaseMatrix(size_t width, size_t height) : width(width), height(height)
	{
		if (0 == width || 0 == height)
		{
			throw std::invalid_argument("Error! Can't create matrix with zero width or height!");
		}

		matrix.resize(width);
		
		for (size_t i = 0; i < width; ++i)
		{
			matrix[i].resize(height);
		}
	}

public:
	virtual float getPixel(size_t x, size_t y) const = 0;

	size_t getHeight() const
	{
		return height;
	}

	size_t getWidth() const
	{
		return width;
	}

	virtual ~BaseMatrix()
	{

	}
};