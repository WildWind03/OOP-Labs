#pragma once

#include "ShotPoint.h"

#include <cstdio>

class ShipPoint : public ShotPoint
{
	bool isVert;
	
public:

	ShipPoint() = delete;

	ShipPoint(size_t height, size_t width, bool isVert) : ShotPoint(height, width)
	{
		this -> isVert = isVert;
	}

	ShipPoint & operator= (const ShipPoint & p) = delete;

	ShipPoint(const ShipPoint & p) : ShotPoint(p)
	{
		this -> isVert = p.isVertical();
	}

	bool isVertical() const
	{
		return isVert;
	}

	virtual ~ShipPoint()
	{
		
	}
};

