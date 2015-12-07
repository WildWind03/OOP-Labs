#pragma once

#include <cstdio>

class ShotPoint
{
	size_t height;
	size_t width;
	
public:

	ShotPoint();
	ShotPoint(const ShotPoint & p);
	ShotPoint(size_t height, size_t width);

	ShotPoint & operator= (const ShotPoint & p);

	size_t getHeight() const;
	size_t getWidth() const;

	virtual ~ShotPoint();
};
