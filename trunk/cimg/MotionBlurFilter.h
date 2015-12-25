#pragma once

#include "BaseFilter.h"
#include "Image.h"

#include <string> 

class MotionBlurFilter : public BaseFilter
{
	size_t angle;
	size_t speed;

	const size_t maxSpeed = 30;
	const size_t maxAngle = 360;

	const unsigned char maxColor = 255;
	const unsigned char minColor = 0;

	const std::string CANT_APPLY_FILTER_STR = "It's impossible to apply filter. Wrong angle or speed. Use --help to learn how to use the program";

	Pixel getNewPixel(const Image & image, int x, int y, int pixelSpeed) const;
	Pixel getNearPixel(const Image & image, int x, int y) const;

public:
	MotionBlurFilter(size_t angle, size_t speed);

	MotionBlurFilter() = delete;
	MotionBlurFilter(const MotionBlurFilter & motionBlurFilter) = delete;
	MotionBlurFilter & operator= (const MotionBlurFilter & motionBlurFilter) = delete;

	virtual Image apply(const Image & image) const override;

	virtual ~MotionBlurFilter();

};