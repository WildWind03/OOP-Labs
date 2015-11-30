#pragma once

#include "GamerType.h"

#include <string>
#include <cstdio>
#include <getopt.h>

class ConsoleParser
{
	std::string consoleGamer  = "ConsoleGamer";
	std::string randomGamer = "RandomGamer";
	std::string optimalGamer = "OptimalGamer";

	GamerType g1, g2;
	size_t countOfRounds;

public:
	ConsoleParser(int argc, char *argv[])
	{
		int c = getopt(argc, argv, "r:f:s:");

		while (c > 0)
		{
			switch(c)
			{
				case 'r' : 
					countOfRounds = atoi(optarg);
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

					break;
			}

			c = getopt(argc, argv, "r:f:s:");
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