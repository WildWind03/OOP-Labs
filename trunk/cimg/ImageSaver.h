#pragma once

#include "Image.h"

#include <string>

class ImageSaver
{
protected:
	ImageSaver() {}

public:
	virtual void save(std::string filePath, const Image & image) = 0;
	virtual ~ImageSaver() {}
};
