#pragma once

#include "Observer.h"
#include "FieldPoint.h"
#include "Field.h"
#include "FieldView.h"
#include <cstdio>

class View : public Observer
{
protected:

	View() {}

public:

	virtual FieldPoint getFieldPoint(const size_t sizeOfShip) = 0;

	virtual void paint(const FieldView & f) = 0;

	virtual ~View() {}
};