#pragma once

#include "ShotPoint.h"

#include <cstdio>

class ShipPoint : public ShotPoint
{
	bool isVert;
	
public:

	ShipPoint() = delete;
	ShipPoint & operator= (const ShipPoint & p) = delete;
	
	ShipPoint(size_t height, size_t width, bool isVert);
	ShipPoint(const ShipPoint & p);

	bool isVertical() const;

	virtual ~ShipPoint();
};

