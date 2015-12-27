#include "GrayscaleFunctor.h"

GrayscaleFunctor::GrayscaleFunctor()
{

}

Pixel GrayscaleFunctor::operator()(const Pixel & pixel) const
{
	float newColor = redK * static_cast<float> (pixel.getRed()) + greenK * static_cast<float>(pixel.getGreen()) + blueK * static_cast<float>(pixel.getBlue());

	return Pixel(static_cast<unsigned char> (newColor), static_cast<unsigned char>(newColor), static_cast<unsigned char>(newColor));
}

GrayscaleFunctor::~GrayscaleFunctor()
{

}
