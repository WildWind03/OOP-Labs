#pragma once

#include "View.h"

class ConsoleGamer : public Gamer
{

	ConsoleView *view;

public:
	
	ConsoleGamer() = delete;
	ConsoleGamer(ConsoleView *view);

	void placeShips();
	void makeShot();

	~ConsoleGamer();
}