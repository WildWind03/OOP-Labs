#include "GamerFactory.h"

Gamer * GamerFactory::CreateGamer(GamerType type)
{
    Gamer * gamer = nullptr;

    if (GamerType::consoleGamer == type)
    {
        gamer = new ConsoleGamer();
    }

    if (GamerType::randGamer == type)
    {
        gamer = new RandGamer();
    }

    if (GamerType::optGamer == type)
    {
        gamer = new OptGamer();
    }

	return gamer;
}