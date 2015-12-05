#pragma once

#include <string>
#include <cstdio>
#include <stdexcept>

class BaseParser
{
protected:

	std::string str;

	//const std::string tooShortStr = "Too short. Try again";
	const std::string yIsNotCorrectStr = "Y is not correct! Try again";
	const std::string xIsNotCorrectStr = "X is not correct! Try again";

	const size_t posOfAInAscii = 65;
	const size_t posOfZInAscii = 90;

	BaseParser(std::string str) : str(str)
	{

	}

	size_t getNumFromStr(std::string str) const
	{
		size_t h;
		
		try
		{
			h = std::stoi(str);
		}
		catch (const std::invalid_argument & er)
		{
			throw std::invalid_argument(yIsNotCorrectStr);
		}

		return h;
	}

	size_t getNumByChar(char c) const
	{
		if (c < posOfAInAscii || c > posOfZInAscii)
		{
			throw std::invalid_argument(xIsNotCorrectStr);
		}

		size_t w = (size_t) (c - posOfAInAscii);

		return w;
	}

public:

	BaseParser() = delete;

	BaseParser(const BaseParser & p) = delete;

	BaseParser & operator= (const BaseParser & p) = delete;

	void takeNewString(std::string str)
	{
		this -> str = str;
	}

	virtual ~BaseParser()
	{

	}

};