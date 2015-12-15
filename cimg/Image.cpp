#include "Image.h"

Image::Image(const std::vector<std::vector<Pixel*>> & pixels)
{
	imagePixels.resize(pixels.size());

	for (size_t i = 0; i < pixels.size(); ++i)
	{
		imagePixels[i].resize(pixels[i].size());
	}

	fillNullptrPixels();


	for (size_t i = 0; i < pixels.size(); ++i)
	{
		for (size_t k = 0; k < pixels[i].size(); ++k)
		{
			imagePixels[i][k] = new Pixel(*(pixels[i][k]));
		}
	}
}

void Image::fillNullptrPixels()
{
	for (size_t i = 0; i < imagePixels.size(); ++i)
	{
		for (size_t k = 0; k < imagePixels[i].size(); ++k)
		{
			imagePixels[i][k] = nullptr;
		}
	}
}

bool Image::isPointInImage(size_t x, size_t y) const
{
	if (x < getWidth() && y < getHeight())
	{
		return true;
	}

	return false;
}

size_t Image::getSize() const
{
	size_t size = getHeight() * getWidth() * sizeof(Pixel);

	return size;
}

Image::Image (const Image & image)
{
	copyImageFrom(image);
}

Image & Image::operator= (const Image & image)
{
	clear();
	copyImageFrom(image);

	return *this;
}

Pixel * Image::getPixel(size_t x, size_t y)
{
	return imagePixels[x][y];
}


const Pixel & Image::getPixel(size_t x, size_t y) const
{
	return *imagePixels[x][y];
}

void Image::copyImageFrom(const Image & image)
{
	imagePixels.resize(image.getWidth());

	for (size_t i = 0; i < image.getWidth(); ++i)
	{
		imagePixels[i].resize(image.getHeight());
	}

	fillNullptrPixels();

	for (size_t i = 0; i < image.getWidth(); ++i)
	{
		for (size_t k = 0; k < image.getHeight(); ++k)
		{
			imagePixels[i][k] = new Pixel(image.getPixel(i, k));
		}
	}
}

size_t Image::getWidth() const
{
	return imagePixels.size();
}

size_t Image::getHeight() const
{
	if (0 == getWidth())
	{
		return 0;
	}
	else
	{
		return imagePixels[0].size();
	}
}

void Image::clear()
{
	for (size_t i = 0; i < getWidth(); ++i)
	{
		for (size_t k = 0; k < getHeight(); ++k)
		{
			delete imagePixels[i][k];
		}
	}
}

Image::~Image()
{
	clear();
}
