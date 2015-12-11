#pragma once

#include <stdexcept>
#include <string>

class GameExitException : public BattleShipException
{

public:
	GameExitException(std::string str) : BattleShipException(str)
	{

	}

	GameExitException & operator= (const GameExitException & except) = delete;
	GameExitException() = delete;
	GameExitException(const GameExitException & except) : BattleShipException(except.what())
	{

	}

	virtual ~GameExitException()
	{

	}
};