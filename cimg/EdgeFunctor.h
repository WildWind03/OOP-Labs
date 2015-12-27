#pragma once

#include "BaseFunctor.h"

#include <string>

class EdgeFunctor : public BaseFunctor
{
	const unsigned char threshold;

	const unsigned char MAX_COLOR = 255;
	const unsigned char MIN_COLOR = 0;

	const std::string INCORRECT_INPUT_STR = "Can't apply edge filter! Wrong input!";
	
public:
	EdgeFunctor(unsigned char threshold);

	EdgeFunctor() = delete;
	EdgeFunctor(const EdgeFunctor & edgeFunctor) = default;
	EdgeFunctor & operator= (const EdgeFunctor & edgeFunctor) = default;

	virtual Pixel operator()(const Pixel & pixel) const override;

	virtual ~EdgeFunctor();
};