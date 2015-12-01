#pragma once

#include <cstdio>

class ShotPoint
{
	const size_t height;
	const size_t width;
	
public:
	ShotPoint() = delete;

	ShotPoint(const ShotPoint & p) : height(p.getHeight()), width(p.getWidth())
	{
		
	}

	ShotPoint & operator= (const ShotPoint & p) = delete;

	ShotPoint(size_t height, size_t width) : height(height), width(width)
	{

	}

	size_t getHeight() const
	{
		return height;
	}

	size_t getWidth() const
	{
		return width;
	}

	virtual ~ShotPoint()
	{
		
	}
};
