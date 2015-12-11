#pragma once

#include "BannedActionException.h"

#include <cstdio>
#include <string>

class Ship
{
	const std::string alreadyDestr = "Error! Trying to destroy the ship which has already destroyed!";

	size_t size;
	size_t destrSize;

public:

	Ship(size_t size);

	Ship() = delete;
	Ship & operator=(const Ship & s) = delete;
	Ship (const Ship & s) = delete;

	void takeDamage();

	size_t getSize() const;
	size_t getDestrSize() const;
	bool isDestroyed() const;

	virtual ~Ship();
};
