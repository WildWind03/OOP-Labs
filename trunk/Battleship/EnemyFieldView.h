#pragma once

#include "FieldView.h"

class EnemyFieldView : public FieldView
{
	bool isShot(const size_t h, const size_t w) const;
	bool isShip(const size_t h, const size_t w) const;

	bool isExistDestrCellNear(size_t h, size_t w) const;
	bool isExistDestrCellNear(size_t h, size_t w, size_t hExcept, size_t wExcept) const;
	
public:

	EnemyFieldView(const Field & myField, const ShotField & shots);

	EnemyFieldView() = delete;
	EnemyFieldView(const EnemyFieldView & f) = delete;
	EnemyFieldView & operator= (const EnemyFieldView & f) = delete;

	virtual CellState getCellState(const size_t h, const size_t w) const;

	bool isExistLearntDestrCellNear(const ShotPoint & p) const;
	bool isExistLearntDestrCellNear(const ShotPoint & p, const ShotPoint & exceptIt) const;

	virtual ~EnemyFieldView();

};