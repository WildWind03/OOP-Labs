#include "GrayscaleFunctor.h"

GrayscaleFunctor::GrayscaleFunctor()
{

}

Pixel GrayscaleFunctor::operator()(const Pixel & pixel) const
{
	unsigned char newColor = redK * pixel.getRed() + greenK * pixel.getGreen() + blueK * pixel.getBlue();

	return Pixel(newColor, newColor, newColor);
}

GrayscaleFunctor::~GrayscaleFunctor()
{

}
