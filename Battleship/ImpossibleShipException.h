#pragma once

#include "BattleShipException.h"

class ImpossibleShipException : public BattleShipException
{
public:
	ImpossibleShipException(std::string str) : BattleShipException(str)
	{

	}

	ImpossibleShipException & operator= (const ImpossibleShipException & except) = delete;
	ImpossibleShipException () = delete;
	
	ImpossibleShipException (const ImpossibleShipException & except) : BattleShipException(except.what())
	{

	}

	~ImpossibleShipException()
	{

	}
};