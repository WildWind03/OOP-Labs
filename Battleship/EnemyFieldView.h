#pragma once

#include "FieldView.h"

class EnemyFieldView : public FieldView
{
	
public:

	EnemyFieldView(const Field & myField, const ShotField & shots);

	EnemyFieldView() = delete;
	EnemyFieldView(const EnemyFieldView & f) = delete;
	EnemyFieldView & operator= (const EnemyFieldView & f) = delete;

	bool isShot(const size_t h, const size_t w) const;
	bool isShip(const size_t h, const size_t w) const;

	CellState getCellState(const size_t h, const size_t w) const;

	~EnemyFieldView();

};