#pragma once

class Pixel
{
	unsigned char red = 0;
	unsigned char green = 0;
	unsigned char blue = 0;

public:

	Pixel();

	Pixel (unsigned char red, unsigned char green, unsigned char blue);
	Pixel (const Pixel & pixel);

	unsigned char getRed() const;
	unsigned char getGreen() const;
	unsigned char getBlue() const;

	void setRed(unsigned char red);
	void setGreen(unsigned char green);
	void setBlue(unsigned char blue);


	Pixel & operator= (const Pixel & pixel);

	virtual ~Pixel();
};
