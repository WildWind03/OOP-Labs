#include "RandGamer.h"

RandGamer::RandGamer() : Gamer() 
{

}

bool RandGamer::vertOrHor() const
{
	std::default_random_engine rng;	

	rng.seed(std::random_device()());

    std::uniform_int_distribution<int> dist_a_b(0,1);

    if (0 == dist_a_b(rng))
    {
    	return false
    }
    else
    {
    	return true;
    }	
}

void RandGamer::placeShips(Field *field)
{
	for (size_t i = 1; i < 5; i++)
	{
		for (size_t k = i; k < 5; k++)
		{
			while(1)
			{
				size_t pos = field -> getRandPos();

				bool vertOrHor = RandGame::vertOrHor();

				Cell* myCell = field -> getCellByNum(pos);

				if (field -> isPosCorrectForShip(pos, i, vertOrHor))
				{
					Ship *my_ship = new Ship(vertOrHor, i, pos, field);
					break;
				}
			}

		}
	}
}

RandGamer::~RandGamer()
{

}
