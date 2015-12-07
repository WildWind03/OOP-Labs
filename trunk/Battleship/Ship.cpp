#include "Ship.h"

Ship::Ship(size_t size) : size(size), destrSize(0)
{

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
		throw BannedActionException(alreadyDestr);
	}
}

bool Ship::isDestroyed() const
{
	if (destrSize == size)
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