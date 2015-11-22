#include "RandGamer.h"

RandGamer::RandGamer(Field & myField, Field & aField) : Gamer(myField, aField) 
{
	
}

FieldPoint RandGamer::getPoint(const size_t sizeOfShip)
{
	size_t h = myRand::getRand(0, myField.getHeight() - 1);
	size_t w = myRand::getRand(0, myField.getWidth() - 1);

	bool isVert = myRand::getRand(0,1);

	FieldPoint p(h, w, isVert);

	return p;
}

RandGamer::~RandGamer()
{

}
