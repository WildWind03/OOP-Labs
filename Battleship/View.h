#pragma once

#include "Observer.h"
#include "FieldPoint.h"
#include "Field.h"
#include <cstdio>

class View : public Observer
{
protected:

	View() {}

public:

	virtual FieldPoint getFieldPoint(const size_t sizeOfShip) = 0;

	virtual void paint(const Field & f) = 0;

	virtual ~View() {}
};