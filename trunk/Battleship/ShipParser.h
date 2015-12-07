#pragma once

#include "BaseParser.h"
#include "ShipPoint.h"
#include "InvalidInputException.h"
#include "GameExitException.h"

#include <string>
#include <cstdio>

class ShipParser : public BaseParser
{
	const std::string shortStr = "Too short!";
	const std::string exitStr = "Q";

	const size_t minLengthOfCorrectString = 3;


	bool getOrient(char c) const;

public:

	ShipParser(std::string str);

	ShipParser() = delete;
	ShipParser(const ShipParser & p) = delete;
	ShipParser & operator=(const ShipParser & p) = delete;

	ShipPoint parse();

	~ShipParser();
};