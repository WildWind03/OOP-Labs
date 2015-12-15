#include "GrayscaleFunctor.h"

GrayscaleFunctor::GrayscaleFunctor()
{

}

void GrayscaleFunctor::operator()(Pixel * pixel) const
{
	unsigned char newColor = redK * pixel -> getRed() + greenK * pixel -> getGreen() + blueK * pixel -> getBlue();

	pixel -> setRed(newColor);
	pixel -> setGreen(newColor);
	pixel -> setBlue(newColor);
}

GrayscaleFunctor::~GrayscaleFunctor()
{

}