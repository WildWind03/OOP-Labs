#pragma once

#include "BaseFunctor.h"

#include <cstdio>

class GrayscaleFunctor : public BaseFunctor
{
	const float redK = 0.299;
	const float greenK = 0.587;
	const float blueK = 0.114;
	
public:
	GrayscaleFunctor();

	GrayscaleFunctor(const GrayscaleFunctor & grayscaleFunctor) = delete;
	GrayscaleFunctor & operator= (const GrayscaleFunctor & grayscaleFunctor) = delete;

	virtual void operator()(Pixel * pixel) const override;

	virtual ~GrayscaleFunctor();
};