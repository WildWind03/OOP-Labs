#include "EdgeFunctor.h"

EdgeFunctor::EdgeFunctor(unsigned char threshold) : threshold(threshold)
{

}

void EdgeFunctor::operator()(Pixel * pixel) const
{
	if (pixel -> getRed() > threshold)
	{
		pixel -> setRed(maxColor);
	}
	else
	{
		pixel -> setRed(minColor);
	}

	if (pixel -> getGreen() > threshold)
	{
		pixel -> setGreen(maxColor);
	}
	else
	{
		pixel -> setGreen(minColor);
	}

	if (pixel -> getBlue() > threshold)
	{
		pixel -> setBlue(maxColor);
	}
	else
	{
		pixel -> setBlue(minColor);
	}
}

EdgeFunctor::~EdgeFunctor()
{

}