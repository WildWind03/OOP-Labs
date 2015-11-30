#pragma once

#include "ShipPoint.h"
#include "Field.h"
#include "FieldView.h"
#include "ShotPoint.h"

#include <cstdio>

class View
{
protected:

	View() {}

public:

	virtual ShotPoint getShotPoint() = 0;
	virtual ShipPoint getShipPoint(const size_t sizeOfShip) = 0;

	virtual void paint(const FieldView & f) = 0;

	virtual ~View() {}
};