#pragma once

#include "InvalidInputException.h"

#include <string>
#include <cstdio>

class BaseParser
{
protected:

	std::string str;
	
	const std::string incorrectInputStr = "Invalid input";

	const size_t posOfAInAscii = 65;
	const size_t posOfZInAscii = 90;

	const size_t posOfaInAscii = 97;
	const size_t posOfzInAscii = 122;

	BaseParser(std::string str);

	size_t getNumFromStr(std::string str) const;

	size_t getNumByChar(char c) const;

public:

	BaseParser() = delete;

	BaseParser(const BaseParser & p) = delete;

	BaseParser & operator= (const BaseParser & p) = delete;

	void takeNewString(std::string str);

	virtual ~BaseParser();

};