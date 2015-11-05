#pragma once

class GamerFactory
{

	const string consoleGamer = "ConsoleGamer";
	const string randGamer = "RandGamer";
	const string optGamer = "OptGamer";

public:

	static Gamer* CreateGamer(std::string type)
	{
		switch(type)
		{
			case consoleGamer :

				return ConsoleGamer();

				break;

			case randGamer :

				return RandGamer();

				break;

			case OptGamer :

				return OptGamer();

				break;
		}
	}
};