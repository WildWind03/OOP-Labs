#pragma once

#include "ShotPoint.h"
#include "BaseParser.h"

#include <string>
#include <cstdio>

class ShotParser : public BaseParser
{
	const size_t minLengthOfCorrectString = 3;

public:
	ShotParser(std::string str) : BaseParser(str)
	{

	}

	ShotParser() = delete;
	ShotParser(const ShotParser & p) = delete;
	ShotParser & operator=(const ShotParser & p) = delete;

	ShotPoint parse()
	{
		if (str.size() < minLengthOfCorrectString)
		{
			throw std::runtime_error(tooShortStr);
		}

		char x = str[0];

		std::string y = str.substr(2);

		size_t h = getNumFromStr(y);
		size_t w = getNumByChar(x);

		return ShotPoint(h, w);
	}

	~ShotParser()
	{

	}

};