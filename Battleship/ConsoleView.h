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
	const std::string beginGameStr = "The game has been begun!";
	const std::string winStr = "Congratulations! You have won!";
	const std::string loseStr = "Not bad! But you are loser!";
	const std::string wrongHeightStr = "Error! The height of the field is incorrect!";
	
	const std::string missedStr = "Missed!";
	const std::string injuredStr = "Injured!";
	const std::string destrStr = "Destroyed";

	const size_t posOfAInAscii = 65;

	const size_t maxHeightOfField = 26;

	std::ifstream in;

public:

	ConsoleView();
	ConsoleView(const ConsoleView & v) = delete;
	ConsoleView & operator= (const ConsoleView & v) = delete;

	virtual ShotPoint getShotPoint();
	virtual ShipPoint getShipPoint(const size_t sizeOfShip);

	void printError(const std::exception & er);
	void printStatistics(const Statistics & stat);
	void printGameEndedStr(bool isWon);
	void printGameStartedStr();
	void printShotState(ShotState state);

	void paint(const FieldView & f);

	virtual ~ConsoleView();
};