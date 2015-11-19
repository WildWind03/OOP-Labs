#include "RandGamer.h"

RandGamer::RandGamer(Field *myField, Field *aField) : Gamer(myField, aField) 
{
	
}

bool RandGamer::vertOrHor() const
{
	std::default_random_engine rng;

	rng.seed(std::random_device()());

    std::uniform_int_distribution<int> dist_a_b(0,1);

    if (0 == dist_a_b(rng))
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
			while(1)
			{
				size_t pos = myField -> getRandPos();

				bool isVertical = vertOrHor();

				if (myField -> isPosCorrectForShip(pos, i, isVertical))
				{
					Ship *myShip = new Ship(i);

					myField -> addShip(myShip, isVertical , pos);
					
					break;
				}
			}

		}
	}
}

RandGamer::~RandGamer()
{

}
