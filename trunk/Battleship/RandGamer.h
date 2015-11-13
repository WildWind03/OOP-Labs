#pragma once
#include "Field.h"

class RandGamer : public Gamer
{

static bool vertOrHor() const;
	
public:

	RandGamer();

	void placeShips(Field *field);
	void makeShot();

	~RandGamer();
};