#pragma once

#include "Image.h"

#include <string>

class ImageLoader
{
protected:
	ImageLoader() 
	{

	}

public:
	virtual Image load(std::string filePath) = 0;
	virtual ~ImageLoader() 
	{
		
	}
};
