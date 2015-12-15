#include "Pixel.h"



Pixel::Pixel() : red(0), green(0), blue(0)
{

}


Pixel & Pixel::operator= (const Pixel & pixel) 
{
	this -> red = pixel.getRed();
	this -> green = pixel.getGreen();
	this -> blue = pixel.getBlue();
	
	return *this;
}

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

void Pixel::setRed(unsigned char red)
{
	this -> red = red;
}

void Pixel::setGreen(unsigned char green)
{
	this -> green = green;
}

void Pixel::setBlue(unsigned char blue)
{
	this -> blue = blue;
}

Pixel::~Pixel()
{

}