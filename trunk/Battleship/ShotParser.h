#pragma once

#include "ShotPoint.h"
#include "BaseParser.h"
#include "Exceptions.h"

#include <string>
#include <cstdio>

class ShotParser : public BaseParser
{
	const size_t minLengthOfCorrectString = 3;

	const std::string exitStr = "Q";

public:
	ShotParser(std::string str) : BaseParser(str) {}

	ShotParser() = delete;
	ShotParser(const ShotParser & p) = delete;
	ShotParser & operator=(const ShotParser & p) = delete;

	ShotPoint parse()
	{
		if (str == exitStr)
		{
			throw GameExitEvent();
		}

		if (str.size() < minLengthOfCorrectString)
		{
			throw ImpossibleShotError();
		}

		char x = str[0];

		std::string y = str.substr(2);

		size_t h, w;

		try
		{
			h = getNumFromStr(y);
			w = getNumByChar(x);
		}
		catch (const std::invalid_argument & er)
		{
			throw ImpossibleShotError();
		}

		return ShotPoint(h, w);
	}

	~ShotParser() {}
};