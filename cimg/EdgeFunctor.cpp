#include "EdgeFunctor.h"

#include <stdexcept>

EdgeFunctor::EdgeFunctor(int threshold)
{
	if (threshold > 255 || threshold < 0)
	{
		throw std::invalid_argument(INCORRECT_INPUT_STR);
	}

	this -> threshold = static_cast<unsigned char> (threshold);
}

Pixel EdgeFunctor::operator()(const Pixel & pixel) const
{
	if (pixel.getRed() > threshold)
	{
		return Pixel(MAX_COLOR, MAX_COLOR, MAX_COLOR);
	}

	if (pixel.getGreen() > threshold)
	{
		return Pixel(MAX_COLOR, MAX_COLOR, MAX_COLOR);
	}

	if (pixel.getBlue() > threshold)
	{
		return Pixel(MAX_COLOR, MAX_COLOR, MAX_COLOR);
	}

	return Pixel(MIN_COLOR, MIN_COLOR, MIN_COLOR);
}

EdgeFunctor::~EdgeFunctor()
{

}
