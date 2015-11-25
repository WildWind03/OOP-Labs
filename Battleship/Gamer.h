#pragma once

#include "Field.h"
#include "FieldPoint.h"
#include "MyFieldView.h"
#include "EnemyFieldView.h"
#include "SimplePoint.h"

#include <cstdio>


class Gamer
{

protected:

	MyFieldView * myFieldV;
	EnemyFieldView * enemyFieldV;

	Gamer() = delete;

    Gamer(MyFieldView * myFieldV, EnemyFieldView * enemyFieldV) : enemyFieldV(enemyFieldV), myFieldV(myFieldV)
    {
    	
    }

public:

	virtual FieldPoint getPoint(const size_t sizeOfShip)=0;

	virtual SimplePoint getPointForShot()=0;

	virtual ~Gamer()
	{
		delete(enemyFieldV);
		delete(myFieldV);
	}
};
