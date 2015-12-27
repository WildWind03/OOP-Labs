#include "EdgeFunctor.h"

#include <stdexcept>

EdgeFunctor::EdgeFunctor(unsigned char threshold) : threshold(threshold)
{
	if (threshold > MAX_COLOR || threshold < MIN_COLOR)
	{
		throw std::invalid_argument(INCORRECT_INPUT_STR);
	}
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
