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

	static Gamer * CreateGamer(GamerType type, Field & myField, Field & aField)
	{
        Gamer * gm = nullptr;

        if (GamerType::consoleGamer == type)
        {
            gm = new ConsoleGamer(myField, aField);
        }

        if (GamerType::randGamer == type)
        {
            gm = new RandGamer(myField, aField);
        }

        if (GamerType::optGamer == type)
        {
            gm = new OptGamer(myField, aField);
        }

		return gm;
	}
};
