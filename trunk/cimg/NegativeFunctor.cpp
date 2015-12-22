#include "NegativeFunctor.h"

NegativeFunctor::NegativeFunctor()
{

}

Pixel NegativeFunctor::operator()(const Pixel & pixel) const
{

	unsigned char newRed = maxColor - pixel.getRed();
	unsigned char newGreen = maxColor - pixel.getGreen();
	unsigned char newBlue = maxColor - pixel.getBlue();

	Pixel newPixel(newRed, newGreen, newBlue);
	return newPixel;
}

NegativeFunctor::~NegativeFunctor()
{

}
