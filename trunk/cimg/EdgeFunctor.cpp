#include "EdgeFunctor.h"

EdgeFunctor::EdgeFunctor(unsigned char threshold) : threshold(threshold)
{

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
