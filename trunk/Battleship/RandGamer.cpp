#include "RandGamer.h"

RandGamer::RandGamer(Field *myField, Field *aField) : Gamer(myField, aField) 
{
	
}

bool RandGamer::isVertical() const
{
    if (0 == Field::getRand(0,1))
    {
    	return false;
    }
    else
    {
    	return true;
    }	
}

void RandGamer::placeShips()
{
	for (size_t i = 1; i < 5; i++)
	{
		for (size_t k = i; k < 5; k++)
		{
			while(true)
			{
				size_t h = Field::getRand(0, myField -> getHeight());
				size_t w = Field::getRand(0, myField -> getWidth());

				bool isVert = isVertical();

				FieldPoint p(h, w, isVert);

				if (myField -> isPosCorrectForShip(i, p))
				{
					Ship *myShip = new Ship(i);

					myField -> addShip(myShip, p);
					
					break;
				}
			}

		}
	}
}


RandGamer::~RandGamer()
{

}
