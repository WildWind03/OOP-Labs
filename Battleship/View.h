#pragma once

#include "Observer.h"
#include "Point.h"
#include <cstdio>

class View : public Observer
{
protected:

	View() {}

public:

	virtual Point getPlaceForShips() = 0;

	virtual void paint() = 0;

	virtual ~View() {}
};