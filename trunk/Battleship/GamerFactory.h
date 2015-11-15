#pragma once

#include "Gamer.h"
#include "ConsoleGamer.h"
#include "RandGamer.h"
#include "OptGamer.h"
#include <iostream>
#include <string>
#include <cstdio>

class GamerFactory
{

public:

	static Gamer* CreateGamer(std::string type, View *view, Field *myField, Field *aField)
	{
        Gamer *gm = NULL;

        const std::string consoleGamer = "ConsoleGamer";
        const std::string randGamer = "RandGamer";
        const std::string optGamer = "OptGamer";

        if (consoleGamer == type)
        {
            gm = new ConsoleGamer(static_cast<ConsoleView*> (view), myField, aField);
        }

        if (randGamer == type)
        {
            gm = new RandGamer(myField, aField);
        }

        if (optGamer == type)
        {
            gm = new OptGamer(myField, aField);
        }

		return gm;
	}
};
