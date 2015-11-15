#pragma once

#include "View.h"
#include "Point.h"
#include <iostream>
#include <string>
#include <stdexcept>

class ConsoleView : public View
{

	const std::string rangeErrorStr = "Typed cell doesn't exist";
	const std::string orientErrorStr = "Orientation isn't correct. Use 'V' for vertical and 'H' for horizontal orientation";

	size_t getNumByChar(const char c);

	bool getOrient(char c);

public:

	ConsoleView();

	virtual Point getPlaceForShips();

	virtual void paint();

	~ConsoleView();
};
