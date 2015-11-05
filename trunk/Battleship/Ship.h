#pragma once

class Ship
{

	size_t size;
	size_t destrSize;

public:
	
	Ship(bool isVertical, size_t size, size_t pos);

	size_t size();

	bool isDestroyed();
};