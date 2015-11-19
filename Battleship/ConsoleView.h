#pragma once

#include "View.h"
#include "FieldPoint.h"
#include <iostream>
#include <string>
#include <stdexcept>

class ConsoleView : public View
{

	const std::string rangeErrorStr = "Typed cell doesn't exist. Try again";
	const std::string orientErrorStr = "Orientation isn't correct. Use 'V' for vertical and 'H' for horizontal orientations. Try again";
	const std::string busyErrorStr = "The position is busy. Try again";
	const std::string typeStr = "Type a point which you want to place your ship on. Size of ship is ";	

	size_t getNumByChar(char c) const;

	bool getOrient(char c) const;

public:

	ConsoleView();

	virtual FieldPoint getFieldPoint(size_t sizeOfShip);

	virtual void printInputError() const;

	virtual void paint(Field *f);

	virtual ~ConsoleView();
};