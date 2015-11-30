#pragma once

#include "BaseParser.h"
#include "ShipPoint.h"

#include <string>
#include <cstdio>

class ShipParser : public BaseParser
{

	const std::string orientationErrorStr = "Orientation isn't correct! Try again";

	bool getOrient(char c) const
	{
		if ('V' == c)
		{
			return true;
		}

		if ('H' != c)
		{
			throw std::runtime_error(orientationErrorStr);
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
		if (str.size() < 3)
		{
			throw std::runtime_error(tooShortStr);
		}

		char x = str[0];

		char isVertC = str[str.size() - 1];
		bool isVert = getOrient(isVertC);

		std::string y = str.substr(2, str.size() - 4);

		size_t h = getNumFromStr(y);
		size_t w = getNumByChar(x);

		return ShipPoint(h, w, isVert);
	}

	~ShipParser()
	{

	}

};