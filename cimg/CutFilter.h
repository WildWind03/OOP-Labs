#pragma once

#include "BaseFilter.h"

#include <string> 

class CutFilter : public BaseFilter
{
	const size_t newWidth;
	const size_t newHeight;

	const std::string CANT_APPLY_FILTER_STR = "It's impossible to apply filter. Wrong width and height";

public:
	CutFilter(size_t newWidth, size_t newHeight);

	CutFilter() = delete;
	CutFilter(const CutFilter & cutFilter) = delete;
	CutFilter & operator= (const CutFilter & cutFilter) = delete;

	virtual Image apply(const Image & image) const override;

	virtual ~CutFilter();

};