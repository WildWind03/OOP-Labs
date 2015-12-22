#pragma once

#include "BaseFilter.h"
#include "Image.h"
#include "Pixel.h"

#include <string> 
#include <vector>
#include <stdexcept>

class CutFilter : public BaseFilter
{
	size_t newWidth;
	size_t newHeight;

	const std::string CANT_APPLY_FILTER_STR = "It's impossible to apply filter. Wrong width and height";

public:
	CutFilter(size_t newWidth, size_t newHeight);

	CutFilter() = delete;
	CutFilter(const CutFilter & cutFilter) = delete;
	CutFilter & operator= (const CutFilter & cutFilter) = delete;

	virtual Image apply(const Image & image) const override;

	virtual ~CutFilter();

};