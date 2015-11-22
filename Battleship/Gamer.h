#pragma once

#include "Field.h"
#include "FieldPoint.h"

#include <cstdio>


class Gamer
{

protected:

	Field & enemyField;
	Field & myField;

	Gamer() = delete;

    Gamer(Field & myField, Field & aField) : enemyField(enemyField), myField(myField)
    {
    	
    }

public:

	virtual FieldPoint getPoint(const size_t sizeOfShip)=0;

	virtual void makeShot()=0;
};
