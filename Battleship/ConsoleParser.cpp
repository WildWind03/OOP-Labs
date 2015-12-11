#include "ConsoleParser.h"

ConsoleParser::ConsoleParser(int argc, char *argv[])
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
					throw InvalidInputException(incorrectCountOfRounds);
				}
				break;

			case 'f' :
				if (randomGamer == optarg)
				{
					gamer1Type = GamerType::randGamer;
				}
				if (optimalGamer == optarg)
				{
					gamer1Type = GamerType::optGamer;
				}
				if (consoleGamer == optarg)
				{
					gamer1Type = GamerType::consoleGamer;
				}
				++countF;

				if ((optarg != consoleGamer) && (optarg != optimalGamer) && (optarg != randomGamer))
				{
					throw InvalidInputException (incorrectGamerType);
				}

				break;

			case 's' :

				if (randomGamer == optarg)
				{
					gamer2Type = GamerType::randGamer;
				}
				if (optimalGamer == optarg)
				{
					gamer2Type = GamerType::optGamer;
				}
				if (consoleGamer == optarg)
				{
					gamer2Type = GamerType::consoleGamer;
				}

				++countS;

				if ((optarg != consoleGamer) && (optarg != optimalGamer) && (optarg != randomGamer))
				{
					throw InvalidInputException (incorrectGamerType);
				}

				break;

			case 'h' :

				isHelp = true;

				break;
		}

		c = getopt(argc, argv, "r:f:s:h");
	}

	if (true == isHelp)
	{
		std::cout << helpStr << std::endl;
		exit(0);
	}

	if ((countR != 1) || (countF != 1) || (countS != 1))
	{
		std::cout << incorrectInput << std::endl;
		exit(0);
	}		
}

size_t ConsoleParser::getCountOfRounds()
{
	return countOfRounds;
}

GamerType ConsoleParser::getFirstGamerType()
{
	return gamer1Type;
}

GamerType ConsoleParser::getSecondGamerType()
{
	return gamer2Type;
}

ConsoleParser::~ConsoleParser()
{

}