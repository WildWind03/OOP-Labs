#pragma once

#include "Image.h"

class BaseFilter
{

protected:

	BaseFilter() 
	{

	}

public:

	virtual Image * apply(const Image & image) = 0;

	virtual ~BaseFilter() 
	{
		
	}

};