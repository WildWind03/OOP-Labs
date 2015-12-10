#pragma once

class Pixel
{
	unsigned char red, blue, green;

public:

	Pixel() = delete;

	Pixel (unsigned char red, unsigned char green, unsigned char blue);
	Pixel (const Pixel & pixel);

	unsigned char getRed() const;
	unsigned char getGreen() const;
	unsigned char getBlue() const;


	Pixel & operator= (const Pixel & pixel) = delete;

	~Pixel();
};