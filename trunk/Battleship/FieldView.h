#pragma once 

#include "Field.h"
#include "ShotField.h"
#include "CellState.h"

class FieldView
{

protected:

	const Field & myField;
	const ShotField & shots;

	FieldView(const Field & myField, const ShotField & shots);

public:

	FieldView() = delete;
	FieldView (const ShotField & sf) = delete;
	FieldView & operator= (const FieldView & f) = delete;

	size_t getWidth() const;
	size_t getHeight() const;
	size_t getSize() const;

	virtual CellState getCellState(const size_t y, const size_t x) const = 0;

	virtual ~FieldView();

};