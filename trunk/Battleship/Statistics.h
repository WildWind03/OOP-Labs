#pragma once

#include <cstddef>

class Statistics
{
	size_t g1Vic;
	size_t g2Vic;

public:

	Statistics();

	Statistics(const Statistics & statistics) = delete;
	Statistics & operator=(const Statistics & statistics) = delete;

	void addG1Vic();
	void addG2Vic();

	size_t getG1Vic() const;
	size_t getG2Vic() const;

	size_t getCountOfGames() const;

	~Statistics();
};