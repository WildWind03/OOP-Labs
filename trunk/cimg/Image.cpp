#include "Image.h"

#include <stdexcept>

Image::Image(const std::vector<std::vector<Pixel>> & pixels)
{
	if (0 == pixels.size())
	{
		throw std::invalid_argument("Error! Image can't have null width!");
	}
	else
	{
		size_t correctHeight = pixels[0].size();

		for (size_t k = 0; k < pixels.size(); ++k)
		{
			if (0 == pixels[k].size())
			{
				throw std::invalid_argument("Error! Image can't have null height!");
			}

			if (pixels[k].size() != correctHeight)
			{
				throw std::invalid_argument("Error! Can't create image by not square array!");
			}
		}
	}

	width = pixels.size();
	height = pixels[0].size();

	imagePixels.resize(width);

	for (size_t i = 0; i < width; ++i)
	{
		imagePixels[i].resize(height);
	}

	for (size_t i = 0; i < width; ++i)
	{
		for (size_t k = 0; k < height; ++k)
		{
			imagePixels[i][k] = pixels[i][k];
		}
	}
}

bool Image::isPointInImage(int x, int y) const
{
	if (x < static_cast<int> (getWidth()) && y < static_cast<int> (getHeight()) && x >= 0 && y >= 0)
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
	copyImageFrom(image);

	return *this;
}

const Pixel & Image::getPixel(size_t x, size_t y) const
{
	return imagePixels[x][y];
}

void Image::copyImageFrom(const Image & image)
{
	imagePixels.resize(image.getWidth());

	for (size_t i = 0; i < image.getWidth(); ++i)
	{
		imagePixels[i].resize(image.getHeight());
	}

	for (size_t i = 0; i < image.getWidth(); ++i)
	{
		for (size_t k = 0; k < image.getHeight(); ++k)
		{
			imagePixels[i][k] = image.getPixel(i, k);
		}
	}

	width = image.getWidth();
	height = image.getHeight();
}

size_t Image::getWidth() const
{
	return width;
}

size_t Image::getHeight() const
{
	return height;
}

Image::~Image()
{

}
