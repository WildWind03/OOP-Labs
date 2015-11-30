#pragma once

#include <string>
#include <cstdio>

#include "ShotPoint.h"

class ShotParser
{
	std::string str;
	const std::string wrongInputStr = "Wrong input! Try again";

	size_t getNumFromStr(std::string str) const
	{
		for (size_t i = 0; i < str.size(); ++i)
		{
			if ((str[i] < '0') || (str[i] > '9')) 
			{
				throw std::bad_cast(wrongInputStr);
			}
		}

		size_t h = std::stoi(str);

		return h;
	}

	size_t getNumByChar(char c) const
	{
		const char posOfA = 65;
		const char posOfLastChar = 90;

		if (c < posOfA || c > posOfLastChar)
		{
			throw std::bad_cast(wrongInputStr);
		}

		size_t w = (size_t) (c - posOfA);

		return w;
	}
}

public:
	ShotParser(std::string str)
	{
		this -> str = str;
	}

	ShotParser()
	{

	}

	ShotParser(const ShotParser & p) = delete;
	ShotParser & operator=(const ShotParser & p) = delete;

	takeNewString(std::string str)
	{
		this -> str = str;
	}

	ShotPoint parse()
	{
		if (str.size() < 3)
		{
			throw (std::bad_cast(wrongInputStr));
		}

		char x = str[0];

		std::string y = str.substr(2);

		size_t h = getNumFromStr(y);
		size_t w = getNumByChar(x);

		return ShotPoint(h, w);
	}

	~Parser()
	{

	}

};