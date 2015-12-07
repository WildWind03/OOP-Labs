#pragma once

#include "BattleShipException.h"

class InvalidInputException : public BattleShipException
{
	std::string errorStr;

public:
	InvalidInputException(std::string str) : BattleShipException(str)
	{

	}
	
	InvalidInputException & operator= (const InvalidInputException & except) = delete;
	InvalidInputException() = delete;

	InvalidInputException(const InvalidInputException & except) : BattleShipException(except.what())
	{

	}

	~InvalidInputException()
	{

	}
};