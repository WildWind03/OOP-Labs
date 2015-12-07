#pragma once

#include "GamerType.h"
#include "InvalidInputException.h"

#include <string>
#include <cstdio>
#include <getopt.h>
#include <iostream>

class ConsoleParser
{
	const std::string incorrectCountOfRounds = "Error! There is incorrect count of rounds!";
	const std::string incorrectGamerType = "Error! Incorrect gamer type!";
	const std::string incorrectInput = "Error! Incorrect input!";
	const std::string helpStr = "Examples: \n"
								"./BattleShip.out -f ConsoleGamer -s RandomGamer -r 10 \n"
								"./BattleShip.out -f OptimalGamer -s RandomGamer -r 1 \n"
								"[-r] - count of rounds \n"
								"[-f] - type of first gamer \n"
								"[-s] - type of second gamer \n"
								"Possible types of gamer : ConsoleGamer, RandomGamer, OptimalGamer";

	const std::string consoleGamer  = "ConsoleGamer";
	const std::string randomGamer = "RandomGamer";
	const std::string optimalGamer = "OptimalGamer";

	GamerType gamer1Type, gamer2Type;

	size_t countOfRounds;

public:

	ConsoleParser(int argc, char *argv[]);

	size_t getCountOfRounds();

	GamerType getFirstGamerType();
	GamerType getSecondGamerType();

	ConsoleParser() = delete;
	ConsoleParser(const ConsoleParser & p) = delete;
	ConsoleParser & operator=(const ConsoleParser & p) = delete;

	~ConsoleParser();

};