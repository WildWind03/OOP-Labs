#pragma once

#include "Pixel.h"

#include <vector>
#include <string>

class Image
{
	std::vector<std::vector<Pixel*>> imagePixels;

	void copyImageFrom(const Image & image);

	void clear();
	void fillNullptrPixels();

public:
	Image() = delete;

	Image (const std::vector<std::vector<Pixel*>> & pixels);
	Image (const Image & image);
	Image & operator= (const Image & image);

	Pixel * getPixel(size_t x, size_t y);
	const Pixel & getPixel(size_t x, size_t y) const;

	size_t getHeight() const;
	size_t getWidth() const;
	size_t getSize() const;

	bool isPointInImage(size_t x, size_t y) const;

	virtual ~Image();
};
