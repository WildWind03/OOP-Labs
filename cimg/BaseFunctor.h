#pragma once

#include "Pixel.h"

class BaseFunctor
{

protected:

	BaseFunctor() 
	{

	}

public:

	virtual void operator()(Pixel * pixel) const = 0;
	
	virtual ~BaseFunctor() 
	{

	}
};