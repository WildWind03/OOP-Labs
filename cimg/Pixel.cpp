#include "Pixel.h"


Pixel::Pixel (unsigned char red, unsigned char green, unsigned char blue) 
											: red(red), green(green), blue(blue)
{

}

Pixel::Pixel (const Pixel & pixel) 
					: red (pixel.getRed()), green(pixel.getGreen()), blue(pixel.getBlue())
{

}

unsigned char Pixel::getRed() const
{
	return red;
}

unsigned char Pixel::getGreen() const
{
	return green;
}

unsigned char Pixel::getBlue() const
{
	return blue;
}

Pixel::~Pixel()
{

}