#include "Ship.h"

Ship::Ship(size_t size)
{
	this -> size = size;
	this -> destrSize = 0;
}

size_t Ship::getSize() const
{
	return size;
}

size_t Ship::getDestrSize() const
{
	return destrSize;
}

void Ship::takeDamage()
{
	if (false == isDestroyed())
	{
		++destrSize;
	}
	else
	{
		throw std::range_error(alreadyDestr);
	}
}

bool Ship::isDestroyed() const
{
	if (this -> destrSize == this -> size)
	{
		return true;
	}
	else
	{
		return false;
	}
}

Ship::~Ship()
{
	
}