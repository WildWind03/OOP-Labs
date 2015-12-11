#pragma once

#include "FieldView.h"

class EnemyFieldView : public FieldView
{
	bool isShot(const size_t y, const size_t x) const;
	bool isShip(const size_t y, const size_t x) const;

	bool isExistDestrCellNear(size_t y, size_t x) const;
	bool isExistDestrCellNear(size_t y, size_t x, size_t yExcept, size_t xExcept) const;
	
public:

	EnemyFieldView(const Field & myField, const ShotField & shots);

	EnemyFieldView() = delete;
	EnemyFieldView(const EnemyFieldView & f) = delete;
	EnemyFieldView & operator= (const EnemyFieldView & f) = delete;

	virtual CellState getCellState(const size_t y, const size_t x) const override;

	bool isExistLearntDestrCellNear(const ShotPoint & p) const;
	bool isExistLearntDestrCellNear(const ShotPoint & p, const ShotPoint & exceptIt) const;

	virtual ~EnemyFieldView();

};