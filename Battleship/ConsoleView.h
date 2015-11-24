#pragma once

#include "View.h"
#include "FieldPoint.h"
#include "Field.h"
#include "FieldView.h"

#include <iostream>
#include <string>
#include <stdexcept>

class ConsoleView : public View
{

	const std::string rangeErrorStr = "Typed cell doesn't exist. Try again";
	const std::string orientErrorStr = "Orientation isn't correct. Use 'V' for vertical and 'H' for horizontal orientations. Try again";
	const std::string inputErrorStr = "It's impossible to place the ship there. Try again";
	const std::string typeStr = "Type a point which you want to place your ship on. Size of ship is ";	

	size_t getNumByChar(char c) const;

	bool getOrient(char c) const;

public:

	ConsoleView();

	virtual FieldPoint getFieldPoint(const size_t sizeOfShip);

	virtual void printError(const std::string er) const;

	virtual void paint(const FieldView & f);

	virtual ~ConsoleView();
};