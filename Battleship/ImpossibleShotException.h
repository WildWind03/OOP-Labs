#pragma once

#include "BattleShipException.h"

class ImpossibleShotException : public BattleShipException
{
	std::string errorStr;

public:
	ImpossibleShotException(std::string str) : BattleShipException(str)
	{

	}

	ImpossibleShotException & operator= (const ImpossibleShotException & except) = delete;
	ImpossibleShotException() = delete;

	ImpossibleShotException(const ImpossibleShotException & except) : BattleShipException(except.what())
	{

	}

	virtual ~ImpossibleShotException()
	{

	}
};