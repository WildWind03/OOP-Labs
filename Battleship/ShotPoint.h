#pragma once

#include <cstdio>

class ShotPoint
{
	size_t height;
	size_t width;
	
public:
	ShotPoint() = delete;

	ShotPoint(const ShotPoint & p)
	{
		this -> height = p.getHeight();
		this -> width = p.getWidth();
	}

	ShotPoint & operator= (const ShotPoint & p) = delete;

	ShotPoint(size_t height, size_t width)
	{
		this -> height = height;
		this -> width = width;
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
