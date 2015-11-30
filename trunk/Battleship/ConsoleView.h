#pragma once

#include "View.h"
#include "ShipPoint.h"
#include "ShotParser.h"
#include "ShipParser.h"
#include "Field.h"
#include "FieldView.h"
#include "ShotPoint.h"
#include "ShotState.h"
#include "Statistics.h"

#include <iostream>
#include <string>
#include <stdexcept>
#include <fstream>

class ConsoleView : public View
{
	const std::string typeStr = "Type a point which you want to place your ship on. Size of ship is ";	
	const std::string typeShotStr = "Type a point which you want to strike on";
	
	const std::string missedStr = "Missed!";
	const std::string injuredStr = "Injured!";
	const std::string destrStr = "Destroyed";

	std::ifstream in;

public:

	ConsoleView();
	ConsoleView(const ConsoleView & v) = delete;
	ConsoleView & operator= (const ConsoleView & v) = delete;

	virtual ShotPoint getShotPoint();
	virtual ShipPoint getShipPoint(const size_t sizeOfShip);

	virtual void printError(const std::exception & er);
	virtual void printStatistics(const Statistics & stat);
	virtual void printGameEndedStr(bool isWon);
	virtual void printGameStartedStr();

	virtual void paint(const FieldView & f);

	virtual void typeShotState(ShotState state);

	virtual ~ConsoleView();
};