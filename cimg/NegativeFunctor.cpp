#include "NegativeFunctor.h"

NegativeFunctor::NegativeFunctor()
{

}

void NegativeFunctor::operator()(Pixel * pixel) const
{
	unsigned char newRed = maxColor - pixel -> getRed();
	unsigned char newGreen = maxColor - pixel -> getGreen();
	unsigned char newBlue = maxColor - pixel -> getBlue();

	pixel -> setRed(newRed);
	pixel -> setGreen(newGreen);
	pixel -> setBlue(newBlue);
}

NegativeFunctor::~NegativeFunctor()
{

}