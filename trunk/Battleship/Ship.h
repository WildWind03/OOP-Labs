#pragma once

#include <cstdio>

class Ship
{
	size_t size;
	size_t destrSize;

public:

	Ship(size_t size);

	size_t getSize() const;

	size_t getDestrSize() const;

	bool isDestroyed() const;

	~Ship();
};
