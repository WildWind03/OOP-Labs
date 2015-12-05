#pragma once

#include "BaseParser.h"
#include "ShipPoint.h"
#include "Exceptions.h"

#include <string>
#include <cstdio>

class ShipParser : public BaseParser
{
	const std::string exitStr = "Q";

	const size_t minLengthOfCorrectString = 5;


	bool getOrient(char c) const
	{
		if ('V' == c)
		{
			return true;
		}

		if ('H' != c)
		{
			throw ImpossibleShipError();
		}

		return false;
	}

public:

	ShipParser(std::string str) : BaseParser(str)
	{

	}

	ShipParser() = delete;
	ShipParser(const ShipParser & p) = delete;
	ShipParser & operator=(const ShipParser & p) = delete;

	ShipPoint parse()
	{
		if (str == exitStr)
		{
			throw GameExitEvent();
		}
		
		if (str.size() < minLengthOfCorrectString)
		{
			throw ImpossibleShipError();
		}

		char x = str[0];

		char isVertC = str[str.size() - 1];
		bool isVert = getOrient(isVertC);

		std::string y = str.substr(2, str.size() - 4);

		size_t h = getNumFromStr(y);
		size_t w = getNumByChar(x);
		
		try
		{
			h = getNumFromStr(y);
			w = getNumByChar(x);
		}
		catch (const std::invalid_argument & er)
		{
			throw ImpossibleShipError();
		}

		return ShipPoint(h, w, isVert);
	}

	~ShipParser()
	{

	}

};