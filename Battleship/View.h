#pragma once

#include "Observer.h"

class View : public Observer
{

	static size_t getNumByChar(const char & c) const;

	const size_t height;
	const size_t width;

	size_t getHeight() const;
	size_t getWidth() const;

public:

	View() = delete;

	virtual size_t getPlaceForShips();

	virtual void paint();

	virtual ~View();
};