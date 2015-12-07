#pragma once

#include <stdexcept>
#include <string>

class BattleShipException : public std::exception
{
	std::string errorStr;

public:
	BattleShipException(std::string str) : errorStr(str) 
	{

	}

	BattleShipException & operator= (const BattleShipException & except) = delete;
	BattleShipException() = delete;

	BattleShipException(const BattleShipException & except) : errorStr(except.what())
	{

	}

	virtual const char * what() const noexcept(true)
	{
		return errorStr.c_str();
	}

	~BattleShipException() 
	{
		
	}
};