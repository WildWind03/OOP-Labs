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
    
	static Gamer * CreateGamer(GamerType type);
};
