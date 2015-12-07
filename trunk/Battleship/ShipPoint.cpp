#include "ShipPoint.h"

ShipPoint::ShipPoint(size_t height, size_t width, bool isVert) : ShotPoint(height, width)
{
	this -> isVert = isVert;
}

ShipPoint::ShipPoint(const ShipPoint & p) : ShotPoint(p)
{
	this -> isVert = p.isVertical();
}

bool ShipPoint::isVertical() const
{
	return isVert;
}

ShipPoint::~ShipPoint()
{
	
}