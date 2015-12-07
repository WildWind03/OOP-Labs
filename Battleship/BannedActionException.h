#pragma once

#include "BattleShipException.h"

class BannedActionException : public BattleShipException
{
public:
	BannedActionException(std::string str) : BattleShipException(str)
	{

	}
	
	BannedActionException & operator= (const BannedActionException & except) = delete;
	BannedActionException() = delete;

	BannedActionException(const BannedActionException & except) : BattleShipException(except.what())
	{

	}

	~BannedActionException()
	{

	}
};