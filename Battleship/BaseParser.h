#pragma once

#include <string>
#include <cstdio>
#include <stdexcept>

class BaseParser
{
protected:

	std::string str;

	const std::string tooShortStr = "Too short. Try again";
	const std::string yIsNotCorrectStr = "Y is not correct! Try again";
	const std::string xIsNotCorrectStr = "X is not correct! Try again";

	BaseParser(std::string str)
	{
		this -> str = str;
	}

	BaseParser() = delete;

	size_t getNumFromStr(std::string str) const
	{
		for (size_t i = 0; i < str.size(); ++i)
		{
			if ((str[i] < '0') || (str[i] > '9')) 
			{
				throw std::runtime_error(yIsNotCorrectStr);
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
			throw std::runtime_error(xIsNotCorrectStr);
		}

		size_t w = (size_t) (c - posOfA);

		return w;
	}

public:

	BaseParser(const BaseParser & p) = delete;

	void takeNewString(std::string str)
	{
		this -> str = str;
	}

	virtual ~BaseParser()
	{

	}

};