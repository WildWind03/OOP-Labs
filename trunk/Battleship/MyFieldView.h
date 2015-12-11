#pragma once

#include "FieldView.h"

class MyFieldView : public FieldView
{
	
	bool isShot(const size_t y, const size_t x) const;
	bool isShip(const size_t y, const size_t x) const;
	
public:

	MyFieldView(const Field & myField, const ShotField & shots);

	MyFieldView() = delete;
	MyFieldView(const MyFieldView & f) = delete;
	MyFieldView & operator= (const MyFieldView & f) = delete;

	virtual CellState getCellState(const size_t y, const size_t x) const override;

	virtual ~MyFieldView();

};