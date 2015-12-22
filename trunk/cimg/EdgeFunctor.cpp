#include "EdgeFunctor.h"

EdgeFunctor::EdgeFunctor(unsigned char threshold) : threshold(threshold)
{

}

Pixel EdgeFunctor::operator()(const Pixel & pixel) const
{
	Pixel newPixel;

	if (pixel.getRed() > threshold)
	{
		newPixel.setRed(maxColor);
	}
	else
	{
		newPixel.setRed(minColor);
	}

	if (pixel.getGreen() > threshold)
	{
		newPixel.setGreen(maxColor);
	}
	else
	{
		newPixel.setGreen(minColor);
	}

	if (pixel.getBlue() > threshold)
	{
		newPixel.setBlue(maxColor);
	}
	else
	{
		newPixel.setBlue(minColor);
	}

	return newPixel;
}

EdgeFunctor::~EdgeFunctor()
{

}
