#pragma once

#include "BaseFunctor.h"

#include <cstdio>

class EdgeFunctor : public BaseFunctor
{
	const unsigned char threshold;

	const unsigned char maxColor = 255;
	const unsigned char minColor = 0;
	
public:
	EdgeFunctor(unsigned char threshold);

	EdgeFunctor() = delete;
	EdgeFunctor(const EdgeFunctor & edgeFunctor) = delete;
	EdgeFunctor & operator= (const EdgeFunctor & edgeFunctor) = delete;

	virtual void operator()(Pixel * pixel) const override;

	virtual ~EdgeFunctor();
};