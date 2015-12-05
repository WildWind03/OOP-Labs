#pragma once

#include <cstdio>

class ShotPoint
{
	size_t height;
	size_t width;
	
public:
	ShotPoint() : height(0), width(0) {}
	ShotPoint(const ShotPoint & p) : height(p.getHeight()), width(p.getWidth()) {}
	ShotPoint(size_t height, size_t width) : height(height), width(width) {}

	ShotPoint & operator= (const ShotPoint & p)
	{
		height = p.getHeight();
		width = p.getWidth();

		return *this;
	}

	size_t getHeight() const
	{
		return height;
	}

	size_t getWidth() const
	{
		return width;
	}

	virtual ~ShotPoint() {}
};
