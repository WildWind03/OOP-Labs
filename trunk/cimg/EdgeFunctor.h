#pragma once

#include "BaseFunctor.h"

#include <cstdio>
#include <stdexcept>
#include <string>

class EdgeFunctor : public BaseFunctor
{
	unsigned char threshold;

	const unsigned char MAX_COLOR = 255;
	const unsigned char MIN_COLOR = 0;

	const std::string INCORRECT_INPUT_STR = "Can't apply edge filter! Wrong input!";
	
public:
	EdgeFunctor(int threshold);

	EdgeFunctor() = delete;
	EdgeFunctor(const EdgeFunctor & edgeFunctor) = delete;
	EdgeFunctor & operator= (const EdgeFunctor & edgeFunctor) = delete;

	virtual Pixel operator()(const Pixel & pixel) const override;

	virtual ~EdgeFunctor();
};