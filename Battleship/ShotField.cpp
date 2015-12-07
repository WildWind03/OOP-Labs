#include "ShotField.h"

ShotField::ShotField(const size_t h, const size_t w) : height(h), width(w)
{
	for (size_t i = 0; i < getSize(); ++i)
	{
		marked.push_back(false);
	}
}

bool ShotField::isPointInField(const size_t h, const size_t w) const
{
	if ((h >= getHeight()) || (w >= getWidth()))
	{
		return false;
	}

	return true;
}

void ShotField::clear()
{
	for (size_t i = 0; i < marked.size(); ++i)
	{
		marked[i] = false;
	}
}

size_t ShotField::getPosFromPoint(const size_t h, const size_t w) const
{
	return h * getWidth() + w;
}

size_t ShotField::getHeight() const
{
	return height;
}

size_t ShotField::getWidth() const
{
	return width;
}

size_t ShotField::getSize() const
{
	return getHeight() * getWidth();
}

bool ShotField::isMarked(const size_t h, const size_t w) const
{
	if (false == isPointInField(h, w))
	{
		throw BannedActionException(outFieldStr);
	}

	size_t pos = getPosFromPoint(h, w);

	return marked[pos];
}

void ShotField::mark(const size_t h, const size_t w)
{
	if (false == isPointInField(h, w))
	{
		throw BannedActionException(outFieldStr);
	}

	size_t pos = getPosFromPoint(h, w);

	marked[pos] = true;
}

void ShotField::unMark(const size_t h, const size_t w)
{
	if (false == isPointInField(h, w))
	{
		throw BannedActionException(outFieldStr);
	}
	
	size_t pos = getPosFromPoint(h, w);

	marked[pos] = false;
}

ShotField::~ShotField()
{

}