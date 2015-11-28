#pragma once

#include "Gamer.h"
#include "ConsoleGamer.h"
#include "RandGamer.h"
#include "OptGamer.h"
#include "GamerType.h"

#include <iostream>
#include <string>
#include <cstdio>

class GamerFactory
{

public:

	static Gamer * CreateGamer(GamerType type)
	{
        Gamer * gm = nullptr;

        if (GamerType::consoleGamer == type)
        {
            gm = new ConsoleGamer();
        }

        if (GamerType::randGamer == type)
        {
            gm = new RandGamer();
        }

        if (GamerType::optGamer == type)
        {
            gm = new OptGamer();
        }

		return gm;
	}
};
