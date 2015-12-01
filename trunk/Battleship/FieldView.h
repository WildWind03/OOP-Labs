#pragma once 

#include "Field.h"
#include "ShotField.h"
#include "CellState.h"

class FieldView
{

protected:

	const Field & myField;
	const ShotField & shots;

	FieldView(const Field & myField, const ShotField & shots) : myField(myField), shots(shots) 
	{
		
	}

public:

	FieldView() = delete;
	FieldView (const ShotField & sf) = delete;
	FieldView & operator= (const FieldView & f) = delete;

	size_t getWidth() const
	{
		return myField.getWidth();
	}
	size_t getHeight() const
	{
		return myField.getHeight();
	}
	size_t getSize() const
	{
		return myField.getSize();
	}

	virtual CellState getCellState(const size_t h, const size_t w) const = 0;

	virtual ~FieldView()
	{
		
	}

};