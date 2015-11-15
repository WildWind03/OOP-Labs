#pragma once

#include "Field.h"

#include <cstdio>


class Gamer
{

protected:

	Field *enemyField;
	Field *myField;

	Gamer() = delete;

    Gamer(Field *myField, Field *aField) 
    {
    	this -> myField = myField;
    	this -> enemyField = aField;
    }

public:

	virtual void placeShips()=0;

	virtual void makeShot()=0;
};
