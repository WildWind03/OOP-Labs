#pragma once

#include "BaseFunctor.h"

#include <cstdio>

class EdgeFunctor : public BaseFunctor
{
	const unsigned char threshold;

	const unsigned char MAX_COLOR = 255;
	const unsigned char MIN_COLOR = 0;
	
public:
	EdgeFunctor(unsigned char threshold);

	EdgeFunctor() = delete;
	EdgeFunctor(const EdgeFunctor & edgeFunctor) = delete;
	EdgeFunctor & operator= (const EdgeFunctor & edgeFunctor) = delete;

	virtual Pixel operator()(const Pixel & pixel) const override;

	virtual ~EdgeFunctor();
};