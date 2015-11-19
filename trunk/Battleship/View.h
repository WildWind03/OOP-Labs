#pragma once

#include "Observer.h"
#include "Point.h"
#include "Field.h"
#include <cstdio>

class View : public Observer
{
protected:

	View() {}

public:

	virtual Point getPlaceForShips(size_t n) = 0;

	virtual void paint(Field *f) = 0;

	virtual ~View() {}
};