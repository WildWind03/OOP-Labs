#pragma once

#include "GamerType.h"

#include <string>
#include <cstdio>
#include <getopt.h>

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
								"Possible types : ConsoleGamer, RandomGamer, OptimalGamer";

	const std::string consoleGamer  = "ConsoleGamer";
	const std::string randomGamer = "RandomGamer";
	const std::string optimalGamer = "OptimalGamer";

	GamerType g1, g2;
	size_t countOfRounds;

public:

	ConsoleParser(int argc, char *argv[])
	{
		int c = getopt(argc, argv, "r:f:s:h");

		bool isHelp = false;

		size_t countR = 0;
		size_t countF = 0;
		size_t countS = 0;

		while (c > 0)
		{
			switch(c)
			{
				case 'r' : 
					try
					{
						countOfRounds = std::stoi(optarg);
						++countR;
					}
					catch (const std::invalid_argument & er)
					{
						throw std::invalid_argument(incorrectCountOfRounds);
					}
					break;

				case 'f' :
					if (randomGamer == optarg)
					{
						g1 = GamerType::randGamer;
					}
					if (optimalGamer == optarg)
					{
						g1 = GamerType::optGamer;
					}
					if (consoleGamer == optarg)
					{
						g1 = GamerType::consoleGamer;
					}
					++countF;

					if ((optarg != consoleGamer) && (optarg != optimalGamer) && (optarg != randomGamer))
					{
						throw std::invalid_argument (incorrectGamerType);
					}

					break;

				case 's' :

					if (randomGamer == optarg)
					{
						g2 = GamerType::randGamer;
					}
					if (optimalGamer == optarg)
					{
						g2 = GamerType::optGamer;
					}
					if (consoleGamer == optarg)
					{
						g2 = GamerType::consoleGamer;
					}

					++countS;

					if ((optarg != consoleGamer) && (optarg != optimalGamer) && (optarg != randomGamer))
					{
						throw std::invalid_argument (incorrectGamerType);
					}

					break;

				case 'h' :

					isHelp = true;

					break;
			}

			c = getopt(argc, argv, "r:f:s:");
		}

		if (true == isHelp)
		{
			std::cout << helpStr << std::endl;

			exit(0);
		}

		if ((countR != 1) || (countF != 1) || (countS != 1))
		{
			throw std::invalid_argument(incorrectInput);
		}		
	}

	size_t getCountOfRounds()
	{
		return countOfRounds;
	}

	GamerType getFirstGamerType()
	{
		return g1;
	}

	GamerType getSecondGamerType()
	{
		return g2;
	}

	ConsoleParser() = delete;
	ConsoleParser(const ConsoleParser & p) = delete;
	ConsoleParser & operator=(const ConsoleParser & p) = delete;

	~ConsoleParser()
	{

	}

};