#pragma once

#include "ShotPoint.h"
#include "BaseParser.h"
#include "GameExitException.h"
#include "InvalidInputException.h"

#include <string>
#include <cstdio>

class ShotParser : public BaseParser
{
	const size_t minLengthOfCorrectString = 2;

	const std::string exitStr = "Q";
	const std::string tooShortError = "Too short";

public:
	ShotParser(std::string str);

	ShotParser() = delete;
	ShotParser(const ShotParser & p) = delete;
	ShotParser & operator=(const ShotParser & p) = delete;

	ShotPoint parse();

	virtual ~ShotParser();
};