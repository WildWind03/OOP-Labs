#include "CutFilter.h"

CutFilter::CutFilter(size_t newWidth, size_t newHeight) : newWidth(newWidth), newHeight(newHeight)
{
	if (0 == newWidth || 0 == newHeight)
	{
		throw std::invalid_argument(CANT_APPLY_FILTER_STR);
	}
	pixels.resize(newWidth);

	for (size_t i = 0; i < newWidth; ++i)
	{
		pixels[i].resize(newHeight);
	}

	fillTempPixelsNull();
}

Image * CutFilter::apply(const Image & image)
{
	if (image.getWidth() < newWidth || image.getHeight() < newHeight)
	{
		throw std::invalid_argument(CANT_APPLY_FILTER_STR);
	}

	for (size_t i = 0; i < newWidth; ++i)
	{
		size_t newK = 0;

		for (size_t k = image.getHeight() - 1; k >= image.getHeight() - newHeight; --k)
		{
			Pixel * pixel = new Pixel(image.getPixel(i, k));
			pixels[i][newHeight - newK - 1] = pixel;
			++newK;
		}
	}

	Image * filteredImage = new Image(pixels);

	deleteTempPixels();
	fillTempPixelsNull();	

	return filteredImage;
}

void CutFilter::fillTempPixelsNull()
{
	for (size_t k = 0; k < newWidth; ++k)
	{
		for (size_t i = 0; i < newHeight; ++i)
		{
			pixels[k][i] = nullptr;
		}
	}
}

void CutFilter::deleteTempPixels()
{
	for (size_t i = 0; i < pixels.size(); ++i)
	{
		for (size_t k = 0; k < pixels[i].size(); ++k)
		{
			delete pixels[i][k];
		}
	}
}

CutFilter::~CutFilter()
{
	deleteTempPixels();
}