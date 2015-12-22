#pragma once

#include "Pixel.h"

class BaseFunctor
{

protected:

	BaseFunctor() 
	{

	}

public:

	virtual Pixel operator()(const Pixel & pixel) const = 0;
	
	virtual ~BaseFunctor() 
	{

	}
};