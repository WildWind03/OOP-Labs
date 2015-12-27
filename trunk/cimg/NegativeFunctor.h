#pragma once

#include "BaseFunctor.h"
#include "Pixel.h"

class NegativeFunctor : public BaseFunctor
{
	const unsigned char maxColor = 255;
	
public:
	NegativeFunctor();

	NegativeFunctor(const NegativeFunctor & negativeFunctor) = default;
	NegativeFunctor & operator= (const NegativeFunctor & negativeFunctor) = default;

	virtual Pixel operator()(const Pixel & pixel) const override;

	virtual ~NegativeFunctor();
};