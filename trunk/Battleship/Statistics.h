#pragma once

class Statistics
{
	size_t g1Vic;
	size_t g2Vic;

public:

	Statistics() : g1Vic(0), g2Vic(0)
	{

	}

	void addG1Vic()
	{
		++g1Vic;
	}
	void addG2Vic()
	{
		++g2Vic;
	}

	size_t getG1Vic() const
	{
		return g1Vic;
	}
	size_t getG2Vic() const
	{
		return g2Vic;
	}

	size_t getCountOfGames() const
	{
		return g1Vic + g2Vic;
	}

	Statistics(const Statistics & statistics) = delete;
	Statistics & operator=(const Statistics & statistics) = delete;

	~Statistics()
	{

	}
};