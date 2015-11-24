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

	static Gamer * CreateGamer(GamerType type, MyFieldView * myFieldV, EnemyFieldView * enemyFieldV)
	{
        Gamer * gm = nullptr;

        if (GamerType::consoleGamer == type)
        {
            gm = new ConsoleGamer(myFieldV, enemyFieldV);
        }

        if (GamerType::randGamer == type)
        {
            gm = new RandGamer(myFieldV, enemyFieldV);
        }

        if (GamerType::optGamer == type)
        {
            gm = new OptGamer(myFieldV, enemyFieldV);
        }

		return gm;
	}
};
