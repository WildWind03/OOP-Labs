#pragma once

#include <vector>
#include <iostream>
#include <cstdio>
#include <string>
#include <stdexcept>

class ShotField
{
	const std::string outFieldStr = "There isn't such cell in the field!";

	std::vector <bool> marked;

	const size_t height;
	const size_t width;

	size_t getPosFromPoint(const size_t h, const size_t w)  const;
	bool isPointInField(const size_t h, const size_t w) const;

public:

	ShotField() = delete;
	ShotField (const ShotField & f) = delete;
	ShotField & operator=(const ShotField & f) = delete;

	ShotField(const size_t h, const size_t w);

	bool isMarked(const size_t h, const size_t w) const;

	size_t getHeight() const;
	size_t getWidth() const;
	size_t getSize() const;

	void clear();

	void mark(const size_t h, const size_t w);
	void unMark(const size_t h, const size_t w);

	~ShotField();
};