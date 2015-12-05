#pragma once

#include <stdexcept>

class GameEndedEvent : public std::exception
{
public:
	GameEndedEvent & operator=(const GameEndedEvent & g) = delete;

	GameEndedEvent (const GameEndedEvent & g){}

	GameEndedEvent() : std::exception() {}

	~GameEndedEvent() {}
};

class GameExitEvent : public std::exception
{
public:
	GameExitEvent & operator=(const GameExitEvent & g) = delete;

	GameExitEvent (const GameExitEvent & g) {}

	GameExitEvent() : std::exception() {}

	~GameExitEvent() {}
};

class ImpossibleShotError : public std::exception
{
public:
	ImpossibleShotError & operator=(const ImpossibleShotError & g) = delete;
	ImpossibleShotError (const ImpossibleShotError & g) {}

	ImpossibleShotError() : std::exception() {}

	~ImpossibleShotError() {}
};

class ImpossibleShipError : public std::exception
{
public:
	ImpossibleShipError & operator=(const ImpossibleShipError & g) = delete;
	ImpossibleShipError (const ImpossibleShipError & g) {}

	ImpossibleShipError() : std::exception() {}

	~ImpossibleShipError() {}
};

