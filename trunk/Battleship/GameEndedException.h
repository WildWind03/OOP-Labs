#pragma once

#include "BattleShipException.h"

class GameEndedException : public BattleShipException
{
public:
	GameEndedException(std::string str) : BattleShipException(str)
	{

	}
	
	GameEndedException & operator= (const GameEndedException & except) = delete;
	GameEndedException() = delete;

	GameEndedException(const GameEndedException & except) : BattleShipException(except.what())
	{

	}

	~GameEndedException()
	{

	}
};