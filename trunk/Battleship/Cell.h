#pragma once

#include "Ship.h"
#include <string>
#include <iostream>

class Cell
{
	enum state {FREE, BUSY, DESTROYED};

	state myState;

	Ship *myShip;

	const std::string fr = "FREE";
	const std::string bs = "BUSY";
	const std::string ds = "DESTROYED";

public:
	
	Cell();

	void addShip(Ship *myShip);
	
	bool isFree() const;

	std::string getState() const; 

	void destroy();

	~Cell();
};