#include "ShotPoint.h"

ShotPoint::ShotPoint() : height(0), width(0) 
{

}

ShotPoint::ShotPoint(const ShotPoint & p) : height(p.getHeight()), width(p.getWidth())
{

}

ShotPoint::ShotPoint(size_t height, size_t width) : height(height), width(width) 
{

}

ShotPoint & ShotPoint::operator= (const ShotPoint & p)
{
	height = p.getHeight();
	width = p.getWidth();

	return *this;
}

size_t ShotPoint::getHeight() const
{
	return height;
}

size_t ShotPoint::getWidth() const
{
	return width;
}

ShotPoint::~ShotPoint() 
{
	
}