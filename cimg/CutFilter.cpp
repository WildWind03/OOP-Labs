#include "CutFilter.h"

CutFilter::CutFilter(size_t newWidth, size_t newHeight) : newWidth(newWidth), newHeight(newHeight)
{
	if (0 == newWidth || 0 == newHeight)
	{
		throw std::invalid_argument(CANT_APPLY_FILTER_STR);
	}
}

Image CutFilter::apply(const Image & image) const
{

	std::vector<std::vector<Pixel>> pixels;
	
	pixels.resize(newWidth);

	for (size_t i = 0; i < newWidth; ++i)
	{
		pixels[i].resize(newHeight);
	}

	if (image.getWidth() < newWidth || image.getHeight() < newHeight)
	{
		throw std::invalid_argument(CANT_APPLY_FILTER_STR);
	}

	for (size_t i = 0; i < newWidth; ++i)
	{
		for (size_t k = 0; k < newHeight; ++k)
		{
			pixels[i][k] = image.getPixel(i, k);
		}
	}

	Image filteredImage = Image(pixels);	

	return filteredImage;
}

CutFilter::~CutFilter()
{

}