#pragma once 

class ConsoleView : public ConsoleView
{

public:

	ConsoleView();

	virtual size_t getPlaceForShips();

	virtual void paint();

	~ConsoleView();
}